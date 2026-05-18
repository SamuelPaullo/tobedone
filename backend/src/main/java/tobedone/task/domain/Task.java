package tobedone.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

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
            throw new IllegalStateException("Task is already completed");
        }
        status = TaskStatus.COMPLETED;
        completedAt = Instant.now();
    }
}

