package com.pawgress.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.main.AddTaskActivity;
import com.pawgress.main.adapters.ToDoRecViewAdapter;
import com.pawgress.onboarding.PetNamingActivity;
import com.pawgress.onboarding.PetSelectorActivity;

public class ToDoFragment extends Fragment {

    private ToDoRecViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        RecyclerView toDoRecView = view.findViewById(R.id.toDoRecView);
        adapter = new ToDoRecViewAdapter(getContext());

        toDoRecView.setAdapter(adapter);
        toDoRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button addButton = view.findViewById(R.id.AddButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateRecViewTasks();
    }
}