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

public class InProgressRecViewAdapter extends RecyclerView.Adapter<InProgressRecViewAdapter.ViewHolder> {
    private List<Task> inProgTasks = new ArrayList<>();
    private final Context context;

    public InProgressRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_progress_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = inProgTasks.get(position);
        holder.inProgSubject.setText(task.getSubject());
        String difficulty = context.getString(task.getDifficulty().getLabelResId());
        holder.inProgDifficulty.setText(difficulty);
        holder.inProgName.setText(task.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d");
        String formattedDueDate = task.getDueDate().format(formatter);
        holder.inProgDueDate.setText(formattedDueDate);

        String substasksStats = task.getNbSubtasksDone() + "/" + task.getSubtasks().size();
        holder.inProgSubtasksStats.setText(substasksStats);

        holder.inProgActions.setOnClickListener(v -> {
            Context context = v.getContext();
            PopupMenu popupMenu = new PopupMenu(context, holder.inProgActions);
            popupMenu.inflate(R.menu.task_in_progress_menu);

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
                if (itemId == R.id.actionMarkCompleted) {
                    DataRepository.getInstance().updateTaskStatus(task.getId(), TaskStatus.COMPLETED);
                    updateRecViewTasks();
                    return true;
                } else if (itemId == R.id.actionBackToDo) {
                    DataRepository.getInstance().updateTaskStatus(task.getId(), TaskStatus.TO_DO);
                    updateRecViewTasks();
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
                                updateRecViewTasks();
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
        return inProgTasks.size();
    }

    public void updateRecViewTasks() {
        this.inProgTasks = DataRepository.getInstance().getAllTasksByStatus(TaskStatus.IN_PROGRESS);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView inProgSubject;
        private TextView inProgDifficulty;
        private TextView inProgName;
        private TextView inProgDueDate;
        private TextView inProgSubtasksStats;
        private ImageView inProgActions;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inProgName = itemView.findViewById(R.id.inProgName);
            inProgSubject = itemView.findViewById(R.id.inProgSubject);
            inProgDifficulty = itemView.findViewById(R.id.inProgDifficulty);
            inProgDueDate = itemView.findViewById(R.id.inProgDueDate);
            inProgSubtasksStats = itemView.findViewById(R.id.inProgSubtasksStats);
            inProgActions = itemView.findViewById(R.id.inProgActions);
        }
    }
}
