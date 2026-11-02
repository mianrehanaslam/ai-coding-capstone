package com.example.capstone.task;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long taskId) {
        super("Task not found: " + taskId);
    }
}
