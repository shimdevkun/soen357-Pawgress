package com.pawgress.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.pawgress.R;
import com.pawgress.main.fragments.ActiveTasksFragment;
import com.pawgress.main.fragments.CompletedTasksFragment;
import com.pawgress.main.fragments.DeadlinesFragment;
import com.pawgress.main.fragments.PetHubFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);

        if (savedInstanceState == null) {
            loadFragment(new ActiveTasksFragment());
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            int itemId = item.getItemId();
            if (itemId == R.id.nav_active_tasks) {
                loadFragment(new ActiveTasksFragment());
            } else if (itemId == R.id.nav_deadlines) {
                loadFragment(new DeadlinesFragment());
            } else if (itemId == R.id.nav_completed_tasks) {
                loadFragment(new CompletedTasksFragment());
            } else if (itemId == R.id.nav_pet_hub) {
                loadFragment(new PetHubFragment());
            }
            drawerLayout.closeDrawers();
            return true;
        });

        View headerView = navigationView.getHeaderView(0);
        ImageButton closeDrawerButton = headerView.findViewById(R.id.closeDrawer);
        closeDrawerButton.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        ImageButton navDrawerToggle = findViewById(R.id.navDrawerToggle);
        navDrawerToggle.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView);
            } else {
                drawerLayout.openDrawer(navigationView);
            }
        });

        ImageButton petIcon = findViewById(R.id.navPetHubIcon);
        petIcon.setOnClickListener(v -> {
            loadFragment(new PetHubFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        TextView navTitle = findViewById(R.id.navTitle);
        String fragmentTitle = "";
        if (fragment instanceof ActiveTasksFragment) {
            fragmentTitle = getString(R.string.active_tasks);
        } else if (fragment instanceof DeadlinesFragment) {
            fragmentTitle = getString(R.string.deadlines);
        } else if (fragment instanceof CompletedTasksFragment) {
            fragmentTitle = getString(R.string.completed_tasks);
        } else if (fragment instanceof PetHubFragment) {
            fragmentTitle = getString(R.string.pet_hub);
        }
        navTitle.setText(fragmentTitle);

        ImageButton petIcon = findViewById(R.id.navPetHubIcon);
        if (fragment instanceof PetHubFragment) {
            petIcon.setVisibility(View.GONE);
        } else {
            petIcon.setVisibility(View.VISIBLE);
        }
    }
}