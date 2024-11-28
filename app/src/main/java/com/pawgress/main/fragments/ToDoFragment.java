package com.pawgress.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.main.adapters.ToDoRecViewAdapter;
import com.pawgress.model.DataRepository;
import com.pawgress.model.Task;
import com.pawgress.model.TaskStatus;

import java.util.List;

public class ToDoFragment extends Fragment {

    private RecyclerView toDoRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        toDoRecView = view.findViewById(R.id.toDoRecView);
        List<Task> toDoTasks = DataRepository.getInstance().getAllTasksByStatus(TaskStatus.TO_DO);

        ToDoRecViewAdapter adapter = new ToDoRecViewAdapter(getContext());
        adapter.setTodoTasks(toDoTasks);

        toDoRecView.setAdapter(adapter);
        toDoRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}