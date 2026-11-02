package com.example.capstone.task;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/" + response.getId())).body(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TaskResponse>> findAll(
            @RequestParam(value = "status", required = false) TaskStatus status) {
        return ResponseEntity.ok(taskService.findAll(status));
    }

    @RequestMapping(value = "/{task-id}", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> findById(@PathVariable("task-id") @Positive Long taskId) {
        return ResponseEntity.ok(taskService.findById(taskId));
    }

    @RequestMapping(value = "/{task-id}/status", method = RequestMethod.PATCH)
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable("task-id") @Positive Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request) {
        return ResponseEntity.ok(taskService.updateStatus(taskId, request));
    }

}
