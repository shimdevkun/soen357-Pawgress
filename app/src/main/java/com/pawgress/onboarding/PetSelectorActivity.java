package com.pawgress.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.PetType;

public class PetSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_selector);

        setupToolbar();

        Button btnChooseHamster = findViewById(R.id.btnChooseHamster);
        btnChooseHamster.setOnClickListener(v -> {
            DataRepository.getInstance().getUserPet().setPetType(PetType.HAMSTER);
            Intent intent = new Intent(PetSelectorActivity.this, PetNamingActivity.class);
            intent.putExtra("petType", "hamster");  // Add extra data
            startActivity(intent);
        });

        Button btnChooseDog = findViewById(R.id.btnChooseDog);
        btnChooseDog.setOnClickListener(v -> {
            DataRepository.getInstance().getUserPet().setPetType(PetType.DOG);
            Intent intent = new Intent(PetSelectorActivity.this, PetNamingActivity.class);
            intent.putExtra("petType", "dog");  // Add extra data
            startActivity(intent);
        });

        Button btnChooseBird = findViewById(R.id.btnChooseBird);
        btnChooseBird.setOnClickListener(v -> {
            DataRepository.getInstance().getUserPet().setPetType(PetType.BIRD);
            Intent intent = new Intent(PetSelectorActivity.this, PetNamingActivity.class);
            intent.putExtra("petType", "bird");  // Add extra data
            startActivity(intent);
        });

        Button btnChooseCat = findViewById(R.id.btnChooseCat);
        btnChooseCat.setOnClickListener(v -> {
            DataRepository.getInstance().getUserPet().setPetType(PetType.CAT);
            Intent intent = new Intent(PetSelectorActivity.this, PetNamingActivity.class);
            intent.putExtra("petType", "cat");  // Add extra data
            startActivity(intent);
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_selection);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(R.string.pet_selector);
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
}