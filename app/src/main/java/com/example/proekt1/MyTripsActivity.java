package com.example.proekt1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Trip;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proekt1.models.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyTripsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private List<Trip> tripsList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripsList = new ArrayList<>();
        tripAdapter = new TripAdapter(tripsList);
        recyclerView.setAdapter(tripAdapter);

        loadTrips();
    }

    private void loadTrips() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("trips")
                .whereEqualTo("driverId", userId)  // Changed from 'userId' to 'driverId'
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    tripsList.clear();
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(MyTripsActivity.this, "No trips found", Toast.LENGTH_SHORT).show();
                    } else {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Trip trip = document.toObject(Trip.class);
                            tripsList.add(trip);
                        }
                        tripAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(MyTripsActivity.this, "Error loading trips", Toast.LENGTH_SHORT).show());
    }
    }
