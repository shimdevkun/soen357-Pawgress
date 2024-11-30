package com.pawgress.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pawgress.R;
import com.pawgress.main.MainActivity;
import com.pawgress.model.DataRepository;
import com.pawgress.model.PetType;

import android.util.Log;


public class PetNamingActivity extends AppCompatActivity {

    private EditText petNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_naming);

        setupToolbar();

        // Retrieve the pet type from the intent
        String petType = getIntent().getStringExtra("petType");

        // Find the ImageView by its ID
        ImageView pet_image = findViewById(R.id.pet_image);

        petNameEditText = findViewById(R.id.pet_name);  // Get the EditText for pet name

        // Check which pet type was selected and handle accordingly
        if (petType != null) {
            if (petType.equals("hamster")) {
                // Handle hamster selection
                pet_image.setImageResource(R.drawable.pet_hamster);  // Set the image resource
                Log.d("PetNamingActivity", "Hamster selected");
            } else if (petType.equals("dog")) {
                // Handle dog selection
                pet_image.setImageResource(R.drawable.pet_dog);  // Set the image resource
                Log.d("PetNamingActivity", "Dog selected");
            } else if (petType.equals("bird")) {
                // Handle bird selection
                pet_image.setImageResource(R.drawable.pet_bird);  // Set the image resource
                Log.d("PetNamingActivity", "Bird selected");
            } else if (petType.equals("cat")) {
                // Handle cat selection
                pet_image.setImageResource(R.drawable.pet_cat);  // Set the image resource
                Log.d("PetNamingActivity", "Cat selected");
            }
        }

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {

            // Get the entered pet name from the EditText
            String petName = petNameEditText.getText().toString();

            // Log the entered pet name (or save it elsewhere)
            Log.d("PetNamingActivity", "Entered pet name: " + petName);

            savePetName(petName);

            Intent intent = new Intent(PetNamingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarBackTitle);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(R.string.pet_naming);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePetName(String petName) {
        DataRepository.getInstance().getUserPet().setName(petName);
    }
}