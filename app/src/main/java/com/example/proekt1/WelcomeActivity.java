package com.example.proekt1;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        // Handle click on Log In text
        TextView txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(view -> {
            // Redirect to LoginActivity
            Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
            startActivity(intent);
        });
    }
}
