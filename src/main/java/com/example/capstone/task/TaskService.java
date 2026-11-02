package com.example.capstone.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        Task task = new Task(request.getTitle().trim(), normalize(request.getDescription()));
        return new TaskResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll(TaskStatus status) {
        List<Task> tasks = status == null
                ? taskRepository.findAll()
                : taskRepository.findByStatus(status);
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Long taskId) {
        return new TaskResponse(findTask(taskId));
    }

    @Transactional
    public TaskResponse updateStatus(Long taskId, UpdateTaskStatusRequest request) {
        Task task = findTask(taskId);
        task.changeStatus(request.getStatus());
        return new TaskResponse(task);
    }

    private Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
