package com.example.capstone.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }

    @Test
    void createsTaskWithDefaultTodoStatus() {
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("  Review capstone  ");
        request.setDescription("  Check all evidence  ");

        TaskResponse response = taskService.create(request);

        assertThat(response.getTitle()).isEqualTo("Review capstone");
        assertThat(response.getDescription()).isEqualTo("Check all evidence");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void reportsMissingTask() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task not found: 99");
    }

    @Test
    void updatesExistingTaskStatus() {
        Task task = new Task("Review", null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus(TaskStatus.DONE);

        TaskResponse response = taskService.updateStatus(1L, request);

        assertThat(response.getStatus()).isEqualTo(TaskStatus.DONE);
    }
}
