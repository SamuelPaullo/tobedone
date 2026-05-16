package tobedone.task.api.dto;

import java.time.Instant;
import java.util.UUID;

import tobedone.task.domain.TaskStatus;

public record TaskResponse(UUID id, String title, String status, Instant createdAt, Instant completedAt) {
}

