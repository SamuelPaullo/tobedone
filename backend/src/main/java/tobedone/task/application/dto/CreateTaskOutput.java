package tobedone.task.application.dto;

import java.time.Instant;
import java.util.UUID;

import tobedone.task.domain.TaskStatus;

public record CreateTaskOutput(
	UUID id,
	String title,
	TaskStatus status,
	Instant createdAt,
	Instant completedAt
) {
}
