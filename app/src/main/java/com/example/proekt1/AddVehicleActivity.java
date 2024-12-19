package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proekt1.models.Vehicle;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText edtVehicleModel, edtVehicleYear, edtVehicleColor, edtVehiclePlate, edtVehicleAvailability;
    private Button btnSaveVehicle;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle);  // Create this layout

        db = FirebaseFirestore.getInstance();

        edtVehicleModel = findViewById(R.id.edtVehicleModel);
        edtVehicleYear = findViewById(R.id.edtVehicleYear);
        edtVehicleColor = findViewById(R.id.edtVehicleColor);
        edtVehiclePlate = findViewById(R.id.edtVehiclePlate);
        edtVehicleAvailability = findViewById(R.id.edtVehicleAvailability);
        btnSaveVehicle = findViewById(R.id.btnSaveVehicle);

        btnSaveVehicle.setOnClickListener(v -> saveVehicleDetails());
    }

    private void saveVehicleDetails() {
        String model = edtVehicleModel.getText().toString().trim();
        String year = edtVehicleYear.getText().toString().trim();
        String color = edtVehicleColor.getText().toString().trim();
        String plate = edtVehiclePlate.getText().toString().trim();
        String availability = edtVehicleAvailability.getText().toString().trim();

        if (TextUtils.isEmpty(model) || TextUtils.isEmpty(year) || TextUtils.isEmpty(color) || TextUtils.isEmpty(plate) || TextUtils.isEmpty(availability)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Vehicle vehicle = new Vehicle(model, year, color, plate, availability, driverId);

        db.collection("vehicles")
                .add(vehicle)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddVehicleActivity.this, "Vehicle details saved", Toast.LENGTH_SHORT).show();

                    // Redirect to Driver Profile page
                    Intent intent = new Intent(AddVehicleActivity.this, DriverProfileActivity.class);
                    startActivity(intent);
                    finish();  // Close the AddVehicleActivity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVehicleActivity.this, "Error saving vehicle", Toast.LENGTH_SHORT).show();
                });
    }

}
