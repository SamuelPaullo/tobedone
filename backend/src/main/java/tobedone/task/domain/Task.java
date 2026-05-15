package tobedone.task.domain;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tobedone.task.domain.exception.InvalidTaskStateException;

@AllArgsConstructor
@Getter
public class Task {

    private final UUID id;
    private final String title;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant completedAt;

    public static Task create(String title) {
        return new Task(UUID.randomUUID(), title, TaskStatus.OPEN, Instant.now(), null);
    }

    public void markAsComplete() {
        if (status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("Task is already completed");
        }
        status = TaskStatus.COMPLETED;
        completedAt = Instant.now();
    }
}

