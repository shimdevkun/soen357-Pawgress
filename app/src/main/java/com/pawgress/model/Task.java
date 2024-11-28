package com.pawgress.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private static int currentId = 0;
    private final int id;
    private TaskStatus status;
    private String name;
    private String subject;
    private TaskDifficulty difficulty;
    private String description;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private List<Subtask> subtasks;

    private static int getNextId() {
        return ++currentId;
    }
    public Task() {
        this.id = getNextId();
        this.subtasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TaskDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TaskDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public int getNbSubtasksDone() {
        return (int) subtasks.stream().filter(Subtask::isDone).count();
    }
}
