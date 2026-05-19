package tobedone.task.domain;

import lombok.Getter;
import tobedone.task.domain.exception.InvalidTaskStateException;
import tobedone.task.domain.exception.InvalidTaskTitleException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Task {

    private final UUID id;
    private final String title;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant completedAt;

    public Task(UUID id, String title, TaskStatus status, Instant createdAt, Instant completedAt) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.completedAt = completedAt;
        if (status == TaskStatus.COMPLETED && Objects.isNull(completedAt)) {
            throw new InvalidTaskStateException("A completed task must have a completedAt date");
        }
        if(title.isBlank()) {
            throw new InvalidTaskTitleException("A task must have a title");
        }
    }

    public static Task create(UUID id, String title, Instant createdAt) {
        return new Task(
                id,
                title,
                TaskStatus.OPEN,
                createdAt,
                null
        );
    }

    public void markAsComplete() {
        if (status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("Task is already completed");
        }
        status = TaskStatus.COMPLETED;
        completedAt = Instant.now();
    }
}

