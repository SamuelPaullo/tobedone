package tobedone.task.application.dto;

import java.time.Instant;
import java.util.UUID;

public record CompleteTaskOutput(UUID taskId, Instant completedAt) {
}
