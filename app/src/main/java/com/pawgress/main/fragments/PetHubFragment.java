package com.pawgress.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.PetStat;
import com.pawgress.model.UserPet;

public class PetHubFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_hub, container, false);

        ProgressBar healthBar = view.findViewById(R.id.healthBar);
        ProgressBar happinessBar = view.findViewById(R.id.happinessBar);
        ProgressBar satietyBar = view.findViewById(R.id.satietyBar);
        TextView healthPercentage = view.findViewById(R.id.healthPercentage);
        TextView happinessPercentage = view.findViewById(R.id.happinessPercentage);
        TextView satietyPercentage = view.findViewById(R.id.satietyPercentage);
        TextView petName = view.findViewById(R.id.petName);
        ImageView petImage = view.findViewById(R.id.petImage);

        UserPet userPet = DataRepository.getInstance().getUserPet();
        healthPercentage.setText(userPet.getPercentageStat(PetStat.HEALTH));
        happinessPercentage.setText(userPet.getPercentageStat(PetStat.HAPPINESS));
        satietyPercentage.setText(userPet.getPercentageStat(PetStat.SATIETY));

        healthBar.setProgress(userPet.getHealth());
        happinessBar.setProgress(userPet.getHappiness());
        satietyBar.setProgress(userPet.getSatiety());

        petName.setText(userPet.getName());
        petImage.setImageResource(userPet.getResourceBasedOnStats());
        return view;
    }
}