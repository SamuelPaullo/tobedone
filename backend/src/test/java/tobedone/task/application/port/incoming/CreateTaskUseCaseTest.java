package tobedone.task.application.port.incoming;

import org.junit.jupiter.api.Test;
import tobedone.task.application.TaskUseCasesFactory;
import tobedone.task.application.dto.CreateTaskInput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.exception.TaskTitleConflictException;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.domain.TaskStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateTaskUseCaseTest {

    @Test
    void returnsCreateTaskOutputWhenTaskIsCreatedSuccessfully() {

        String title = "Estudar Clean Architecture";

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);
        when(mapper.toCreateTaskOutput(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return new CreateTaskOutput(task.getId(), task.getTitle(), task.getStatus().name(), task.getCreatedAt(), task.getCompletedAt());
        });

        CreateTaskUseCase useCase = TaskUseCasesFactory.createTaskUseCase(repository, mapper);

        CreateTaskOutput output = useCase.execute(new CreateTaskInput(title));

        assertThat(output.title()).isEqualTo(title);
        assertThat(output.id()).isNotNull();
        assertThat(output.status()).isEqualTo(TaskStatus.OPEN.name());
        assertThat(output.createdAt()).isNotNull();
        assertThat(output.completedAt()).isNull();

        verify(repository).save(any(Task.class));
        verify(mapper).toCreateTaskOutput(any(Task.class));
    }

    @Test
    void throwsNullPointerExceptionWhenInputIsNull() {

        TaskRepository repository = mock(TaskRepository.class);
        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CreateTaskUseCase useCase = TaskUseCasesFactory.createTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(null))
                .isInstanceOf(NullPointerException.class);

        verify(repository, never()).save(any(Task.class));
        verify(mapper, never()).toCreateTaskOutput(any(Task.class));
    }

    @Test
    void throwsNullPointerExceptionWhenInputTitleIsNull() {

        TaskRepository repository = mock(TaskRepository.class);
        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CreateTaskUseCase useCase = TaskUseCasesFactory.createTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(new CreateTaskInput(null)))
                .isInstanceOf(NullPointerException.class);

        verify(repository, never()).save(any(Task.class));
        verify(mapper, never()).toCreateTaskOutput(any(Task.class));
    }

    @Test
    void throwsNullPointerExceptionWhenInputTitleIsEmpty() {

        var emptyTitle = "";

        TaskRepository repository = mock(TaskRepository.class);
        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        CreateTaskUseCase useCase = TaskUseCasesFactory.createTaskUseCase(repository, mapper);

        assertThatThrownBy(() -> useCase.execute(new CreateTaskInput(emptyTitle)))
                .isInstanceOf(TaskTitleConflictException.class);

        verify(repository, never()).save(any(Task.class));
        verify(mapper, never()).toCreateTaskOutput(any(Task.class));
    }
}



