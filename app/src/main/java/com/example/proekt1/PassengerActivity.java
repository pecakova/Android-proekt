package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Driver;
import com.example.proekt1.models.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proekt1.models.Driver;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class PassengerActivity extends AppCompatActivity {

    private EditText edtFrom, edtTo;
    private Button btnFindDrivers;
    private RecyclerView recyclerView;
    private DriverAdapter driverAdapter;
    private List<Driver> driverList;
    private FirebaseFirestore db;
    private Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger);

        db = FirebaseFirestore.getInstance();

        edtFrom = findViewById(R.id.edtFrom);
        edtTo = findViewById(R.id.edtTo);
        btnFindDrivers = findViewById(R.id.btnFindDrivers);
        recyclerView = findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PassengerActivity.this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);
        });

        driverList = new ArrayList<>();
        driverAdapter = new DriverAdapter(driverList, driver -> {
            String from = edtFrom.getText().toString().trim();
            String to = edtTo.getText().toString().trim();

            if (from.isEmpty() || to.isEmpty()) {
                Toast.makeText(PassengerActivity.this, "Please enter both locations", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send data to Trip Details page
            Intent intent = new Intent(PassengerActivity.this, TripDetailsActivity.class);
            intent.putExtra("driverId", driver.getUserId());
            intent.putExtra("vehicleModel", driver.getVehicleModel());
            intent.putExtra("vehicleColor", driver.getVehicleColor());
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            startActivity(intent);
        });

        recyclerView.setAdapter(driverAdapter);

        btnFindDrivers.setOnClickListener(v -> findAvailableDrivers());
    }

    private void findAvailableDrivers() {
        String from = edtFrom.getText().toString().trim();
        String to = edtTo.getText().toString().trim();

        if (from.isEmpty() || to.isEmpty()) {
            Toast.makeText(PassengerActivity.this, "Please enter both locations", Toast.LENGTH_SHORT).show();
            return;
        }

        driverList.clear(); // Clear the list before populating

        db.collection("users")
                .whereEqualTo("role", "Driver")  // Ensure "Driver" matches in Firestore
                .get()
                .addOnSuccessListener(userSnapshots -> {
                    if (!userSnapshots.isEmpty()) {
                        for (DocumentSnapshot userDoc : userSnapshots) {
                            String driverId = userDoc.getId();
                            String driverName = userDoc.getString("name");

                            db.collection("vehicles")
                                    .whereEqualTo("driverId", driverId)
                                    .whereEqualTo("availability", "yes") // Only available vehicles
                                    .get()
                                    .addOnSuccessListener(vehicleSnapshots -> {
                                        for (DocumentSnapshot vehicleDoc : vehicleSnapshots) {
                                            String vehicleModel = vehicleDoc.getString("model");
                                            String vehicleColor = vehicleDoc.getString("color");
                                            Driver driver = new Driver(driverId, driverName, vehicleModel, vehicleColor);
                                            driverList.add(driver);
                                        }
                                        driverAdapter.notifyDataSetChanged();
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(PassengerActivity.this, "Error fetching drivers", Toast.LENGTH_SHORT).show());
    }
}
