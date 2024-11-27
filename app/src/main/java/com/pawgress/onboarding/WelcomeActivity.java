package com.pawgress.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.pawgress.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        Button btnHomeStart = findViewById(R.id.btnHomeStart);
        btnHomeStart.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, PetSelectorActivity.class);
            startActivity(intent);
        });
    }
}