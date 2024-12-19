package com.example.proekt1;

import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
//    private FirebaseAuth mAuth;
//    private FirebaseFirestore db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//
//        // Check if the user is logged in
//        checkIfLoggedIn();
//    }
//
//    private void checkIfLoggedIn() {
//        // Check if the user is already logged in (authenticated)
//        if (mAuth.getCurrentUser() != null) {
//            // If user is logged in, get their role and redirect to the respective activity
//            String email = mAuth.getCurrentUser().getEmail();
//
//            // Debugging: Check if the email is valid
//            Toast.makeText(this, "User logged in: " + email, Toast.LENGTH_SHORT).show();
//
//            assert email != null;
//            db.collection("users").document(email).get()
//                    .addOnSuccessListener(documentSnapshot -> {
//                        if (documentSnapshot.exists()) {
//                            // Debugging: Check if role exists
//                            String role = documentSnapshot.getString("role");
//                            Toast.makeText(this, "User role: " + role, Toast.LENGTH_SHORT).show();
//
//                            if ("Driver".equals(role)) {
//                                // Redirect to DriverActivity
//                                startActivity(new Intent(MainActivity.this, DriverActivity.class));
//                                finish(); // Prevent going back to MainActivity
//                            } else if ("Passenger".equals(role)) {
//                                // Redirect to PassengerActivity
//                                startActivity(new Intent(MainActivity.this, PassengerActivity.class));
//                                finish(); // Prevent going back to MainActivity
//                            } else {
//                                // Handle case where role is not found
//                                Toast.makeText(this, "Role not found", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            // Handle case where the document does not exist
//                            Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(MainActivity.this, "Error fetching user role", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    });
//        } else {
//            // If no user is logged in, redirect to LoginActivity
//            Toast.makeText(this, "No user logged in, redirecting to Login", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, LogInActivity.class));
//            finish(); // Prevent going back to MainActivity
//        }
//    }
//}
