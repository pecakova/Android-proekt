package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverProfileActivity extends AppCompatActivity {

    private TextView tvDriverName;
    private RecyclerView rvVehicleList;
    private Button btnAddVehicle;
    private Button btnLogout;
    private TextView txtAverageRating;
    private FirebaseFirestore db;
    private String driverId;
    private List<Vehicle> vehicleList;
    private VehicleAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);  // Use your actual layout XML file

        db = FirebaseFirestore.getInstance();
        driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        tvDriverName = findViewById(R.id.tvDriverName);
        rvVehicleList = findViewById(R.id.rvVehicleList);
        btnAddVehicle = findViewById(R.id.btnAddVehicle);

        vehicleList = new ArrayList<>();
        vehicleAdapter = new VehicleAdapter(vehicleList);
        rvVehicleList.setLayoutManager(new LinearLayoutManager(this));
        rvVehicleList.setAdapter(vehicleAdapter);
        btnLogout = findViewById(R.id.btnLogout);
        txtAverageRating = findViewById(R.id.txtAverageRating);

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(DriverProfileActivity.this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);
        });

        // Fetch the driver's info from Firestore
        fetchDriverInfo();

        // Fetch the list of vehicles
        fetchVehicles();

        // Add vehicle button
        btnAddVehicle.setOnClickListener(v -> {
            // Redirect to vehicle addition page
            startActivity(new Intent(DriverProfileActivity.this, AddVehicleActivity.class));
        });
    }

    private void fetchDriverInfo() {
        db.collection("users")
                .document(driverId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Display driver's name or any other information
                        String name = documentSnapshot.getString("name");
                        tvDriverName.setText(name != null ? name : "Driver");
                    } else {
                        Toast.makeText(this, "Driver info not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching driver info", Toast.LENGTH_SHORT).show());
        calculateAverageRatingFromTrips();
    }

    private void fetchVehicles() {
        db.collection("vehicles")
                .whereEqualTo("driverId", driverId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        vehicleList.clear();
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                Vehicle vehicle = document.toObject(Vehicle.class);
                                vehicleList.add(vehicle);
                            }
                            vehicleAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(DriverProfileActivity.this, "Error fetching vehicles", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void calculateAverageRatingFromTrips() {
        db.collection("trips")
                .whereEqualTo("driverId", driverId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        double totalRating = 0.0;
                        int ratingCount = 0;

                        for (var document : querySnapshot) {
                            Double rating = document.getDouble("rating");
                            if (rating != null && rating > 0) { // Only consider valid ratings
                                totalRating += rating;
                                ratingCount++;
                            }
                        }

                        if (ratingCount > 0) {
                            double averageRating = totalRating / ratingCount;
                            txtAverageRating.setText(String.format("Average Rating: %.1f", averageRating));
                        } else {
                            txtAverageRating.setText("No valid ratings available");
                        }
                    } else {
                        txtAverageRating.setText("No trips found for this driver");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load trips", Toast.LENGTH_SHORT).show();
                });
    }
}
