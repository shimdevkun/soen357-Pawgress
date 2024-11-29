package com.pawgress.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataRepository {
    private static DataRepository instance;
    private final UserPet userPet;
    private final List<Task> allTasks;

    private DataRepository() {
        userPet = new UserPet();
        allTasks = new ArrayList<>();
        initData();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    /**
     * Dummy method to fake data to test the different UI screens.
     */
    private void initData() {
        Task todo1 = new Task();
        todo1.setStatus(TaskStatus.TO_DO);
        todo1.setSubject("SOEN 357");
        todo1.setDifficulty(TaskDifficulty.MEDIUM);
        todo1.setName("My Task 1 Name");
        todo1.setDueDate(LocalDate.of(2025, 1, 15));
        todo1.getSubtasks().add(new Subtask(todo1, "Subtask 1-1"));
        todo1.getSubtasks().add(new Subtask(todo1, "Subtask 1-2"));
        allTasks.add(todo1);

        Task todo2 = new Task();
        todo2.setStatus(TaskStatus.TO_DO);
        todo2.setSubject("SOEN 353");
        todo2.setDifficulty(TaskDifficulty.EASY);
        todo2.setName("My Task 2 Name");
        todo2.setDueDate(LocalDate.of(2024, 12, 20));
        todo2.getSubtasks().add(new Subtask(todo2, "Subtask 2-1"));
        allTasks.add(todo2);

        Task progress1 = new Task();
        progress1.setStatus(TaskStatus.IN_PROGRESS);
        progress1.setSubject("SOEN 357");
        progress1.setDifficulty(TaskDifficulty.MEDIUM);
        progress1.setName("My Task 3 Name");
        progress1.setDueDate(LocalDate.of(2024, 12, 23));
        progress1.getSubtasks().add(new Subtask(progress1, "Subtask 3-1"));
        progress1.getSubtasks().add(new Subtask(progress1, "Subtask 3-2", true));
        progress1.getSubtasks().add(new Subtask(progress1, "Subtask 3-3", true));
        progress1.getSubtasks().add(new Subtask(progress1, "Subtask 3-4"));
        allTasks.add(progress1);

        Task progress2 = new Task();
        progress2.setStatus(TaskStatus.IN_PROGRESS);
        progress2.setSubject("MATH 251");
        progress2.setDifficulty(TaskDifficulty.HARD);
        progress2.setName("My Task 4 Name");
        progress2.setDueDate(LocalDate.of(2025, 1, 6));
        progress2.getSubtasks().add(new Subtask(progress2, "Subtask 4-1"));
        progress2.getSubtasks().add(new Subtask(progress2, "Subtask 4-2", true));
        progress2.getSubtasks().add(new Subtask(progress2, "Subtask 4-3", true));
        allTasks.add(progress2);

        userPet.setPetType(PetType.DOG);
    }

    public UserPet getUserPet() {
        return userPet;
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public List<Task> getAllTasksByStatus(TaskStatus status) {
        return allTasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }

    public Task getTaskById(int taskId) {
        for (Task task : allTasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task) {
        allTasks.add(task);
    }

    public void removeTask(int taskId) {
        Task taskToRemove = getTaskById(taskId);
        if (taskToRemove != null) {
            allTasks.remove(taskToRemove);
        }
    }

    public void updateTaskStatus(int taskId, TaskStatus newStatus) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setStatus(newStatus);
        }
    }

    public void updateSubtask(int taskId, int subtaskId, boolean isDone) {
        Task task = getTaskById(taskId);
        if (task != null) {
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.getId() == subtaskId) {
                    subtask.setDone(isDone);
                    return;
                }
            }
        }
    }

    public void updateUserPetStat(PetStat stat, int points) {
        userPet.updateStat(stat, points);
    }
}
