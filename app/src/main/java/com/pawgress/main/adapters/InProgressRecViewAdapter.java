package com.pawgress.main.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.PetStat;
import com.pawgress.model.Subtask;
import com.pawgress.model.Task;
import com.pawgress.model.TaskStatus;
import com.pawgress.model.UserPet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InProgressRecViewAdapter extends RecyclerView.Adapter<InProgressRecViewAdapter.ViewHolder> {
    private static final int defaultPoints = 15;
    private List<Task> inProgTasks = new ArrayList<>();
    private SparseBooleanArray expandedState = new SparseBooleanArray();
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
        bindTaskOverview(holder, task);
        bindActionsMenu(holder, task);
        bindSubtasksToggleState(holder, position, task);
    }

    private void bindTaskOverview(@NonNull ViewHolder holder, Task task) {
        holder.inProgSubject.setText(task.getSubject());
        String difficulty = context.getString(task.getDifficulty().getLabelResId());
        holder.inProgDifficulty.setText(difficulty);
        holder.inProgName.setText(task.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d");
        String formattedDueDate = task.getDueDate().format(formatter);
        holder.inProgDueDate.setText(formattedDueDate);

        String substasksStats = task.getNbSubtasksDone() + "/" + task.getSubtasks().size();
        holder.inProgSubtasksStats.setText(substasksStats);
    }

    private void bindActionsMenu(@NonNull ViewHolder holder, Task task) {
        holder.inProgActions.setOnClickListener(v -> {
            Context context = v.getContext();
            PopupMenu popupMenu = new PopupMenu(context, holder.inProgActions);
            popupMenu.inflate(R.menu.task_in_progress_menu);
            forceMenuIconsDisplay(popupMenu);
            bindActionsMenuOnClickListener(task, popupMenu, context);
            popupMenu.show();
        });
    }

    private static void forceMenuIconsDisplay(PopupMenu popupMenu) {
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
    }

    private void bindActionsMenuOnClickListener(Task task, PopupMenu popupMenu, Context context) {
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.actionMarkCompleted) {
                DataRepository.getInstance().updateTaskStatus(task.getId(), TaskStatus.COMPLETED);
                updateRecViewTasks();

                PetStat statToUpdate = getStatToUpdate(task);
                DataRepository.getInstance().updateUserPetStat(statToUpdate, defaultPoints);
                showTaskCompletionDialog(statToUpdate);
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
    }

    private PetStat getStatToUpdate(Task task) {
        switch (task.getDifficulty()) {
            case EASY:
                return PetStat.HAPPINESS;
            case MEDIUM:
                return PetStat.SATIETY;
            default: // HARD
                return PetStat.HEALTH;
        }
    }

    private void showTaskCompletionDialog(PetStat stat) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_task_completion, null);
        ImageView petImage = dialogView.findViewById(R.id.petImage);
        TextView dialogText = dialogView.findViewById(R.id.dialogText);
        ProgressBar dialogProgressBar = dialogView.findViewById(R.id.dialogProgressBar);
        Button btnContinue = dialogView.findViewById(R.id.btnContinue);

        UserPet userPet = DataRepository.getInstance().getUserPet();
        petImage.setImageResource(userPet.getPetType().getHappyResId());

        String statName = context.getString(stat.getLabelResId());
        String message = context.getString(R.string.task_completion_message, defaultPoints, statName);
        dialogText.setText(message);

        int percentage = userPet.getStat(stat);
        dialogProgressBar.setProgress(percentage);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();
        btnContinue.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void bindSubtasksToggleState(@NonNull ViewHolder holder, int position, Task task) {
        boolean isInitiallyExpanded = expandedState.get(position, false); // Toggle is collapsed by default
        ImageView inProgToggle = holder.inProgToggle;
        RelativeLayout inProgSubtasks = holder.inProgSubtasks;
        if (isInitiallyExpanded) {
            inProgToggle.setImageResource(R.drawable.ic_arrow_drop_up);
            inProgSubtasks.setVisibility(View.VISIBLE);
            loadSubtasks(holder.inProgSubtasksRecView, task.getSubtasks());
        } else {
            inProgToggle.setImageResource(R.drawable.ic_arrow_drop_down);
            inProgSubtasks.setVisibility(View.GONE);
        }

        inProgToggle.setOnClickListener(v -> {
            boolean isCurrentlyExpanded = expandedState.get(position, false);
            if (isCurrentlyExpanded) {
                // Switch from expanded to collapsed
                inProgToggle.setImageResource(R.drawable.ic_arrow_drop_down);
                expandedState.put(position, false);
                inProgSubtasks.setVisibility(View.GONE);
            } else {
                // Switch from collapsed to expanded
                inProgToggle.setImageResource(R.drawable.ic_arrow_drop_up);
                expandedState.put(position, true);
                inProgSubtasks.setVisibility(View.VISIBLE);
                loadSubtasks(holder.inProgSubtasksRecView, task.getSubtasks());
            }
        });
    }

    private void loadSubtasks(RecyclerView inProgSubtasksRecView, List<Subtask> subtasks) {
        SubtaskRecViewAdapter subtaskAdapter = new SubtaskRecViewAdapter(subtasks, this);
        inProgSubtasksRecView.setAdapter(subtaskAdapter);
        inProgSubtasksRecView.setLayoutManager(new LinearLayoutManager(inProgSubtasksRecView.getContext()));
    }

    @Override
    public int getItemCount() {
        return inProgTasks.size();
    }

    public void updateRecViewTasks() {
        this.inProgTasks = DataRepository.getInstance().getAllTasksByStatus(TaskStatus.IN_PROGRESS);
        notifyDataSetChanged();
    }

    public void updateTask(Task updatedTask) {
        int updatedTaskId = updatedTask.getId();
        for (int i = 0; i < inProgTasks.size(); i++) {
            Task task = inProgTasks.get(i);
            if (task.getId() == updatedTaskId) {
                inProgTasks.set(i, updatedTask);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView inProgSubject;
        private TextView inProgDifficulty;
        private TextView inProgName;
        private TextView inProgDueDate;
        private TextView inProgSubtasksStats;
        private ImageView inProgActions;
        private ImageView inProgToggle;
        private RelativeLayout inProgSubtasks;
        
        private RecyclerView inProgSubtasksRecView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inProgName = itemView.findViewById(R.id.inProgName);
            inProgSubject = itemView.findViewById(R.id.inProgSubject);
            inProgDifficulty = itemView.findViewById(R.id.inProgDifficulty);
            inProgDueDate = itemView.findViewById(R.id.inProgDueDate);
            inProgSubtasksStats = itemView.findViewById(R.id.inProgSubtasksStats);
            inProgActions = itemView.findViewById(R.id.inProgActions);
            inProgToggle = itemView.findViewById(R.id.inProgToggle);
            inProgSubtasks = itemView.findViewById(R.id.inProgSubtasks);
            inProgSubtasksRecView = itemView.findViewById(R.id.inProgSubtasksRecView);
        }
    }
}
