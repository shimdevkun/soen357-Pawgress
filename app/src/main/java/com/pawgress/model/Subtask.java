package com.pawgress.model;

public class Subtask {
    private static int currentId = 0;
    private final int id;
    private Task task;
    private String name;
    private boolean done;

    private static int getNextId() {
        return ++currentId;
    }

    public Subtask(Task task, String name) {
        this.id = getNextId();
        this.task = task;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
