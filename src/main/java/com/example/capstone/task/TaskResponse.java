package com.example.capstone.task;

import java.time.Instant;

public class TaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final Instant createdAt;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
