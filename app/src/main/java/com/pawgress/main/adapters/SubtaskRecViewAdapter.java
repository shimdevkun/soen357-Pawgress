package com.pawgress.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.Subtask;
import com.pawgress.model.Task;

import java.util.List;

public class SubtaskRecViewAdapter extends RecyclerView.Adapter<SubtaskRecViewAdapter.ViewHolder> {
    private List<Subtask> subtasks;
    private InProgressRecViewAdapter parentAdapter;

    public SubtaskRecViewAdapter(List<Subtask> subtasks, InProgressRecViewAdapter parentAdapter) {
        this.subtasks = subtasks;
        this.parentAdapter = parentAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subtask subtask = subtasks.get(position);
        holder.chkSubtask.setText(subtask.getName());
        holder.chkSubtask.setChecked(subtask.isDone());

        holder.chkSubtask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Task parentTask = subtask.getTask();
            DataRepository.getInstance().updateSubtask(parentTask.getId(), subtask.getId(), isChecked);
            parentAdapter.updateTask(parentTask);
        });
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkSubtask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chkSubtask = itemView.findViewById(R.id.chkSubtask);
        }
    }
}
