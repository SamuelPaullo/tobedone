package tobedone.task.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public record CompleteTaskResponse(UUID taskId, Instant completedAt) {
}
