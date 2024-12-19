package com.example.proekt1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.proekt1.models.User;

public class RegisterActivity extends AppCompatActivity {

    // Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // UI Elements
    private EditText edtName, edtAge, edtEmail, edtPassword;
    private RadioGroup radioGroupGender, radioGroupUserType;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseApp.initializeApp(this);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupUserType = findViewById(R.id.radioGroupUserType);
        btnRegister = findViewById(R.id.btnRegister);

        // Register button click listener
        btnRegister.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String age = edtAge.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String gender = getSelectedGender();
            String userType = getSelectedUserType();

            if (validateInputs(name, age, email, password, gender, userType)) {
                registerUser(name, age, gender, email, password, userType);
            }
        });
    }

    // Validate input fields
    private boolean validateInputs(String name, String age, String email, String password, String gender, String userType) {
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Name is required");
            return false;
        }
        if (TextUtils.isEmpty(age)) {
            edtAge.setError("Age is required");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            return false;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userType)) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Get the selected gender from radio buttons
    private String getSelectedGender() {
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.radioMale) {
            return "Male";
        } else if (selectedGenderId == R.id.radioFemale) {
            return "Female";
        }
        return "";
    }

    // Get the selected user type (Driver or Passenger)
    private String getSelectedUserType() {
        int selectedUserTypeId = radioGroupUserType.getCheckedRadioButtonId();
        if (selectedUserTypeId == R.id.radioDriver) {
            return "Driver";
        } else if (selectedUserTypeId == R.id.radioPassenger) {
            return "Passenger";
        }
        return "";
    }

    // Register user and save additional data in Firestore
    private void registerUser(String name, String age, String gender, String email, String password, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Create a User object to save in Firestore
                            User newUser = new User(name, age, gender, email, role);

                            // Save user data in Firestore
                            db.collection("users")
                                    .document(user.getUid()) // Use UID as document ID
                                    .set(newUser)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully
                                        Log.d("Register", "User data saved successfully");
                                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                        if ("driver".equalsIgnoreCase(role)) {
                                            // Redirect to DriverVehicleActivity
                                            startActivity(new Intent(RegisterActivity.this, AddVehicleActivity.class));
                                        } else {
                                            // Redirect to PassengerActivity or similar
                                            startActivity(new Intent(RegisterActivity.this, PassengerActivity.class));
                                        }
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error saving user data
                                        Log.e("Register", "Error saving user data", e);
                                        Toast.makeText(RegisterActivity.this, "Error saving user data.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Registration failed
                        Log.w("Register", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
