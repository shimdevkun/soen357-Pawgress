package com.pawgress.model;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private final UserPet userPet;
    private final List<Task> allTasks;

    private DataRepository() {
        userPet = new UserPet();
        allTasks = new ArrayList<>();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public UserPet getUserPet() {
        return userPet;
    }

    public List<Task> getAllTasks() {
        return allTasks;
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
}
