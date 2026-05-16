package tobedone.task;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tobedone.task.api.TaskApiMapper;
import tobedone.task.api.TaskController;
import tobedone.task.api.TaskExceptionHandler;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.input.CompleteTaskUseCase;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.application.port.input.ListTasksUseCase;
import tobedone.task.domain.exception.InvalidTaskStateException;
import tobedone.task.domain.exception.TaskNotFoundException;

class TaskControllerIntegrationTest {

    private MockMvc mockMvc;
    private CreateTaskUseCase createTaskUseCase;
    private ListTasksUseCase listTasksUseCase;
    private CompleteTaskUseCase completeTaskUseCase;

    @BeforeEach
    void setUp() {
        createTaskUseCase = mock(CreateTaskUseCase.class);
        listTasksUseCase = mock(ListTasksUseCase.class);
        completeTaskUseCase = mock(CompleteTaskUseCase.class);

        TaskController controller = new TaskController(
                createTaskUseCase,
                listTasksUseCase,
                completeTaskUseCase,
                new TaskApiMapper()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new TaskExceptionHandler())
                .build();
    }

    @Test
    void shouldCreateTask() throws Exception {
        UUID taskId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Instant now = Instant.parse("2026-05-16T12:00:00Z");

        CreateTaskOutput output = new CreateTaskOutput(taskId, "Estudar Spring Boot", "OPEN", now, null);
        when(createTaskUseCase.execute("Estudar Spring Boot")).thenReturn(output);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Estudar Spring Boot"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Estudar Spring Boot"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldListTasks() throws Exception {
        UUID taskId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        Instant now = Instant.parse("2026-05-16T12:00:00Z");

        TaskOutput output = new TaskOutput(taskId, "Task 1", "OPEN", now, null);
        when(listTasksUseCase.execute()).thenReturn(List.of(output));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskId.toString()))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[0].status").value("OPEN"));
    }

    @Test
    void shouldCompleteTask() throws Exception {
        UUID taskId = UUID.fromString("00000000-0000-0000-0000-000000000003");
        Instant completedAt = Instant.parse("2026-05-16T12:30:00Z");

        CompleteTaskOutput output = new CompleteTaskOutput(taskId, completedAt);
        when(completeTaskUseCase.execute(taskId)).thenReturn(output);

        mockMvc.perform(patch("/tasks/{id}/complete", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(taskId.toString()))
                .andExpect(jsonPath("$.completedAt").value(completedAt.toString()));
    }

    @Test
    void shouldReturn400WhenCreatePayloadIsInvalid() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void shouldReturn404WhenTaskDoesNotExist() throws Exception {
        UUID taskId = UUID.fromString("00000000-0000-0000-0000-000000000004");
        when(completeTaskUseCase.execute(eq(taskId))).thenThrow(new TaskNotFoundException(taskId));

        mockMvc.perform(patch("/tasks/{id}/complete", taskId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("TASK_NOT_FOUND"));
    }

    @Test
    void shouldReturn409WhenTaskStateIsInvalid() throws Exception {
        UUID taskId = UUID.fromString("00000000-0000-0000-0000-000000000005");
        when(completeTaskUseCase.execute(eq(taskId))).thenThrow(new InvalidTaskStateException("Task already completed"));

        mockMvc.perform(patch("/tasks/{id}/complete", taskId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("INVALID_TASK_STATE"));
    }
}
