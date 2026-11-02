package com.example.capstone.task;

import com.example.capstone.CapstoneApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CapstoneApplication.class)
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
    }

    @Test
    void rejectsUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void createsAndReadsTask() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Prepare demo\",\"description\":\"Show evidence\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.title").value("Prepare demo"))
                .andExpect(jsonPath("$.status").value("TODO"));

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Show evidence"));
    }

    @Test
    @WithMockUser
    void rejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    @WithMockUser
    void rejectsOverlongDescription() throws Exception {
        String description = new String(new char[501]).replace('\0', 'x');

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Valid\",\"description\":\"" + description + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    @WithMockUser
    void returnsGenericNotFoundResponse() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }

    @Test
    @WithMockUser
    void changesTaskStatus() throws Exception {
        Task task = taskRepository.save(new Task("Prepare demo", null));

        mockMvc.perform(patch("/api/v1/tasks/{task-id}/status", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"DONE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    @WithMockUser
    void rejectsUnknownStatus() throws Exception {
        Task task = taskRepository.save(new Task("Prepare demo", null));

        mockMvc.perform(patch("/api/v1/tasks/{task-id}/status", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"INVALID\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }
}
