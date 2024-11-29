package com.pawgress.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.main.adapters.InProgressRecViewAdapter;
import com.pawgress.model.DataRepository;
import com.pawgress.model.Task;
import com.pawgress.model.TaskStatus;

import java.util.List;

public class InProgressFragment extends Fragment {

    private InProgressRecViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);

        RecyclerView inProgRecView = view.findViewById(R.id.inProgRecView);
        adapter = new InProgressRecViewAdapter(getContext());

        inProgRecView.setAdapter(adapter);
        inProgRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateRecViewTasks();
    }
}