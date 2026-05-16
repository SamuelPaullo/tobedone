package tobedone.task.application.dto;

import java.time.Instant;
import java.util.UUID;

public record CreateTaskOutput(
        UUID id,
        String title,
        String status,
        Instant createdAt,
        Instant completedAt
) {
}
