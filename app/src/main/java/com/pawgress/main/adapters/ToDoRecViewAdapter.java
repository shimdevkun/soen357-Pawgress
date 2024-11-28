package com.pawgress.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.model.Task;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ToDoRecViewAdapter extends RecyclerView.Adapter<ToDoRecViewAdapter.ViewHolder> {

    private List<Task> todoTasks = new ArrayList<>();
    private Context context;

    public ToDoRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = todoTasks.get(position);
        holder.toDoSubject.setText(task.getSubject());
        String difficulty = context.getString(task.getDifficulty().getLabelResId());
        holder.toDoDifficulty.setText(difficulty);
        holder.toDoName.setText(task.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d");
        String formattedDueDate = task.getDueDate().format(formatter);
        holder.toDoDueDate.setText(formattedDueDate);

        String substasksStats = task.getNbSubtasksDone() + "/" + task.getSubtasks().size();
        holder.toDoSubtasksStats.setText(substasksStats);
    }

    @Override
    public int getItemCount() {
        return todoTasks.size();
    }

    public void setTodoTasks(List<Task> todoTasks) {
        this.todoTasks = todoTasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView toDoSubject;
        private TextView toDoDifficulty;
        private TextView toDoName;
        private TextView toDoDueDate;
        private TextView toDoSubtasksStats;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoName = itemView.findViewById(R.id.toDoName);
            toDoSubject = itemView.findViewById(R.id.toDoSubject);
            toDoDifficulty = itemView.findViewById(R.id.toDoDifficulty);
            toDoDueDate = itemView.findViewById(R.id.toDoDueDate);
            toDoSubtasksStats = itemView.findViewById(R.id.toDoSubtasksStats);
        }
    }
}
