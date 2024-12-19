package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private ProgressBar progressBar; // Add a progress bar for loading indication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar); // Make sure to add a ProgressBar to your layout

        btnLogin.setOnClickListener(view -> loginUser());

        btnRegister.setOnClickListener(view -> {
            // Redirect to RegisterActivity
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LogInActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the progress bar while logging in
        progressBar.setVisibility(View.VISIBLE);

        // Sign in the user using Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Hide the progress bar once the task is complete
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        // If login is successful, get the current user
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LogInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        // Check user role from Firestore
                        checkUserRoleAndRedirect(user);
                    } else {
                        // Authentication failed, show error message
                        Toast.makeText(LogInActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserRoleAndRedirect(FirebaseUser user) {
        if (user != null) {
            // Use the UID of the authenticated user to fetch Firestore document
            db.collection("users").document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Get the role (Driver or Passenger) from Firestore
                            String role = documentSnapshot.getString("role");

                            if (role == null) {
                                // If the role is not assigned in Firestore, handle this case
                                Toast.makeText(LogInActivity.this, "Role not assigned in Firestore", Toast.LENGTH_SHORT).show();
                            } else {
                                // Redirect based on role
                                if ("Driver".equals(role)) {
                                    // Redirect to DriverActivity
                                    startActivity(new Intent(LogInActivity.this, DriverProfileActivity.class));
                                } else if ("Passenger".equals(role)) {
                                    // Redirect to PassengerActivity
                                    startActivity(new Intent(LogInActivity.this, PassengerActivity.class));
                                } else {
                                    // Handle unknown role
                                    Toast.makeText(LogInActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // If user document doesn't exist in Firestore
                            Toast.makeText(LogInActivity.this, "User not found in Firestore", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle Firestore retrieval error
                        Toast.makeText(LogInActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // If the FirebaseUser object is null
            Toast.makeText(LogInActivity.this, "Authentication error. Please log in again.", Toast.LENGTH_SHORT).show();
        }
    }
}
