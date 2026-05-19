package tobedone.task.application.port.incoming;

import org.junit.jupiter.api.Test;
import tobedone.task.application.TaskUseCasesFactory;
import tobedone.task.application.dto.CompleteTaskInput;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.exception.TaskNotFoundException;
import tobedone.task.application.exception.TaskStateConflictException;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.domain.TaskStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompleteTaskUseCaseTest {

    @Test
    void returnsCompletedTaskOutputWhenTaskExistsAndIsOpen() {

        UUID taskId = UUID.randomUUID();
        Task openTask = Task.create(taskId, "Task", Instant.now());

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.findById(taskId)).thenReturn(Optional.of(openTask));
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);
        when(mapper.toCompleteTaskOutput(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return new CompleteTaskOutput(task.getId(), task.getCompletedAt());
        });

        CompleteTaskUseCase useCase = TaskUseCasesFactory.completeTaskUseCase(repository, mapper);

        CompleteTaskOutput output = useCase.execute(new CompleteTaskInput(taskId));

        assertThat(output.taskId()).isEqualTo(taskId);
        assertThat(output.completedAt()).isNotNull();

        verify(repository).save(any(Task.class));
        verify(mapper).toCompleteTaskOutput(any(Task.class));
    }

    @Test
    void shouldTranslateDomainInvalidStateToApplicationException() {

        UUID taskId = UUID.randomUUID();
        Task completedTask = new Task(
                taskId,
                "Task",
                TaskStatus.COMPLETED,
                Instant.now(),
                Instant.now()
        );

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.findById(taskId)).thenReturn(Optional.of(completedTask));

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CompleteTaskUseCase useCase = TaskUseCasesFactory.completeTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(new CompleteTaskInput(taskId)))
                .isInstanceOf(TaskStateConflictException.class)
                .hasMessageContaining("already completed");
    }

    @Test
    void throwsTaskNotFoundWhenTaskDoesNotExist() {

        UUID taskId = UUID.randomUUID();

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CompleteTaskUseCase useCase = TaskUseCasesFactory.completeTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(new CompleteTaskInput(taskId)))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining(taskId.toString());

        verify(repository, never()).save(any(Task.class));
        verify(mapper, never()).toCompleteTaskOutput(any(Task.class));
    }

    @Test
    void throwsNullPointerExceptionWhenInputIsNull() {

        TaskRepository repository = mock(TaskRepository.class);
        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CompleteTaskUseCase useCase = TaskUseCasesFactory.completeTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(null))
                .isInstanceOf(NullPointerException.class);
    }
}

