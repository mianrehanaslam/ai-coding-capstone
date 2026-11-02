package com.example.capstone.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
