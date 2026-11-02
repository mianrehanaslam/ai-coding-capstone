package com.example.capstone.task;

import javax.validation.constraints.NotNull;

public class UpdateTaskStatusRequest {

    @NotNull
    private TaskStatus status;

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
