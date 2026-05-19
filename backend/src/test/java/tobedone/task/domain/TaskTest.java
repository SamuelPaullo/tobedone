package tobedone.task.domain;

import org.junit.jupiter.api.Test;
import tobedone.task.domain.exception.InvalidTaskStateException;
import tobedone.task.domain.exception.InvalidTaskTitleException;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskTest {

    @Test
    void shouldCreateOpenTaskWithNullCompletedAt() {
        Task task = Task.create(UUID.randomUUID(), "Estudar Clean Arch", Instant.now());

        assertThat(task.getStatus()).isEqualTo(TaskStatus.OPEN);
        assertThat(task.getCompletedAt()).isNull();
    }

    @Test
    void shouldMarkTaskAsComplete() {
        Task task = Task.create(UUID.randomUUID(), "Estudar Clean Arch", Instant.now());

        task.markAsComplete();

        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(task.getCompletedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenMarkingAlreadyCompletedTask() {
        Task task = Task.create(UUID.randomUUID(), "Estudar Clean Arch", Instant.now());
        task.markAsComplete();

        assertThatThrownBy(task::markAsComplete)
                .isInstanceOf(InvalidTaskStateException.class)
                .hasMessageContaining("already completed");
    }

    @Test
    void shouldThrowWhenCreatingCompletedTaskWithoutCompletedAt() {
        assertThatThrownBy(() -> new Task(
                UUID.randomUUID(),
                "Estudar Clean Arch",
                TaskStatus.COMPLETED,
                Instant.now(),
                null
        ))
                .isInstanceOf(InvalidTaskStateException.class)
                .hasMessageContaining("completedAt");
    }

    @Test
    void shouldAllowCreatingCompletedTaskWithCompletedAt() {
        Instant completedAt = Instant.now();
        Task task = new Task(UUID.randomUUID(), "Estudar Clean Arch", TaskStatus.COMPLETED, Instant.now(), completedAt);

        assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(task.getCompletedAt()).isEqualTo(completedAt);
    }

    @Test
    void shouldThrowWhenIdIsNull() {
        assertThatThrownBy(() -> new Task(null, "Título", TaskStatus.OPEN, Instant.now(), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowWhenTitleIsNull() {
        assertThatThrownBy(() -> new Task(UUID.randomUUID(), null, TaskStatus.OPEN, Instant.now(), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowWhenTitleIsEmpty() {
        var emptyTitle = " ";
        assertThatThrownBy(() -> Task.create(UUID.randomUUID(), emptyTitle, Instant.now()))
                .isInstanceOf(InvalidTaskTitleException.class);
    }
}

