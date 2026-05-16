package tobedone.task.application.dto;

import java.time.Instant;
import java.util.UUID;

import tobedone.task.domain.TaskStatus;

public record TaskOutput(
	UUID id,
	String title,
	String status,
	Instant createdAt,
	Instant completedAt
) {
}

