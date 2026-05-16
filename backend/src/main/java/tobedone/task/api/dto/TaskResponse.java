package tobedone.task.api.dto;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(UUID id, String title, String status, Instant createdAt, Instant completedAt) {
}

