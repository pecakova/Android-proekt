package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proekt1.models.Trip;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proekt1.models.Trip;
import com.google.firebase.firestore.FirebaseFirestore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proekt1.models.Trip;
import com.google.firebase.firestore.FirebaseFirestore;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proekt1.models.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TripDetailsActivity extends AppCompatActivity {

    private TextView txtFrom, txtTo, txtDriverName, txtVehicleModel, txtVehicleColor;
    private RatingBar ratingBar;
    private Button btnSaveTrip;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        db = FirebaseFirestore.getInstance();

        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);
        txtDriverName = findViewById(R.id.txtDriverName);
        txtVehicleModel = findViewById(R.id.txtVehicleModel);
        txtVehicleColor = findViewById(R.id.txtVehicleColor);
        ratingBar = findViewById(R.id.ratingBar);
        btnSaveTrip = findViewById(R.id.btnSaveTrip);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        // Get trip details and driver ID from Intent
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String driverName = intent.getStringExtra("driverName");
        String vehicleModel = intent.getStringExtra("vehicleModel");
        String vehicleColor = intent.getStringExtra("vehicleColor");
        String driverId = intent.getStringExtra("driverId"); // Add driverId

        // Set the data to views
        txtFrom.setText(from);
        txtTo.setText(to);
        txtDriverName.setText(driverName);
        txtVehicleModel.setText(vehicleModel);
        txtVehicleColor.setText(vehicleColor);

        btnSaveTrip.setOnClickListener(v -> saveTripDetails(from, to, driverName, vehicleModel, vehicleColor, driverId));
    }

    private void saveTripDetails(String from, String to, String driverName, String vehicleModel, String vehicleColor, String driverId) {
        // Validate input (optional)
//        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(to) || TextUtils.isEmpty(driverName) || TextUtils.isEmpty(vehicleModel) || TextUtils.isEmpty(vehicleColor)) {
//            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // Get the rating from RatingBar
        float rating = ratingBar.getRating();

        // Create Trip object
        Trip trip = new Trip("", from, to, driverName, vehicleModel, vehicleColor, driverId, rating);

        // Save trip to Firestore
        db.collection("trips")
                .add(trip)
                .addOnSuccessListener(documentReference -> {
                    // Use Firestore's auto-generated document ID as the tripId
                    trip.setTripId(documentReference.getId());

                    Toast.makeText(TripDetailsActivity.this, "Trip saved successfully!", Toast.LENGTH_SHORT).show();

                    // Redirect to Trip List or Driver Profile page
                    Intent intent = new Intent(TripDetailsActivity.this, MyTripsActivity.class); // Change to desired activity
                    startActivity(intent);
                    finish(); // Close the current activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(TripDetailsActivity.this, "Error saving trip", Toast.LENGTH_SHORT).show();
                });
    }
}
