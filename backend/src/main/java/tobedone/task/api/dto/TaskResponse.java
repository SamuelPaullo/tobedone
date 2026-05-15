package tobedone.task.api.dto;

import java.time.Instant;
import java.util.UUID;

import tobedone.task.domain.TaskStatus;

public record TaskResponse(UUID id, String title, TaskStatus status, Instant createdAt, Instant completedAt) {
}

