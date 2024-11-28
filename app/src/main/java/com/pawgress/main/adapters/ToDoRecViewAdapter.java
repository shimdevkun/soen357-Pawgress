package com.pawgress.main.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.Task;
import com.pawgress.model.TaskStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ToDoRecViewAdapter extends RecyclerView.Adapter<ToDoRecViewAdapter.ViewHolder> {

    private List<Task> todoTasks = new ArrayList<>();
    private final Context context;

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

        holder.toDoActions.setOnClickListener(v -> {
            Context context = v.getContext();
            PopupMenu popupMenu = new PopupMenu(context, holder.toDoActions);
            popupMenu.inflate(R.menu.task_to_do_menu);

            // Force menu icons to be displayed
            try {
                java.lang.reflect.Field popup = popupMenu.getClass().getDeclaredField("mPopup");
                popup.setAccessible(true);
                Object menuPopupHelper = popup.get(popupMenu);
                menuPopupHelper.getClass()
                        .getDeclaredMethod("setForceShowIcon", boolean.class)
                        .invoke(menuPopupHelper, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.actionStart) {
                    DataRepository.getInstance().updateTaskStatus(task.getId(), TaskStatus.IN_PROGRESS);
                    setTodoTasks(DataRepository.getInstance().getAllTasksByStatus(TaskStatus.TO_DO));
                    return true;
                } else if (itemId == R.id.actionDelete) {

                    String deleteTask = context.getString(R.string.deleteTask);
                    String confirmDeleteTask = context.getString(R.string.confirm_delete_task);
                    String actionYes = context.getString(R.string.action_yes);
                    String actionCancel = context.getString(R.string.action_cancel);

                    new AlertDialog.Builder(context)
                            .setTitle(deleteTask)
                            .setMessage(confirmDeleteTask)
                            .setPositiveButton(actionYes, (dialog, which) -> {
                                DataRepository.getInstance().removeTask(task.getId());
                                setTodoTasks(DataRepository.getInstance().getAllTasksByStatus(TaskStatus.TO_DO));
                            })
                            .setNegativeButton(actionCancel, (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .create()
                            .show();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });
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
        private ImageView toDoActions;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoName = itemView.findViewById(R.id.toDoName);
            toDoSubject = itemView.findViewById(R.id.toDoSubject);
            toDoDifficulty = itemView.findViewById(R.id.toDoDifficulty);
            toDoDueDate = itemView.findViewById(R.id.toDoDueDate);
            toDoSubtasksStats = itemView.findViewById(R.id.toDoSubtasksStats);
            toDoActions = itemView.findViewById(R.id.toDoActions);
        }
    }
}
