package tobedone.task.application.port.incoming;

import org.junit.jupiter.api.Test;
import tobedone.task.application.TaskUseCasesFactory;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ListTasksUseCaseTest {

    @Test
    void returnsListOfTaskOutputsWhenTasksExist() {

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.findAll()).thenReturn(List.of(
                Task.create(UUID.randomUUID(), "Task 1", Instant.now()),
                Task.create(UUID.randomUUID(), "Task 2", Instant.now())
        ));

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);
        when(mapper.toTaskOutput(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return new TaskOutput(task.getId(), task.getTitle(), task.getStatus().name(), task.getCreatedAt(), task.getCompletedAt());
        });

        ListTasksUseCase useCase = TaskUseCasesFactory.listTasksUseCase(repository, mapper);

        List<TaskOutput> outputs = useCase.execute();

        assertThat(outputs).hasSize(2);
        assertThat(outputs.get(0).title()).isEqualTo("Task 1");
        assertThat(outputs.get(1).title()).isEqualTo("Task 2");

        verify(repository).findAll();
        verify(mapper, times(2)).toTaskOutput(any(Task.class));
    }

    @Test
    void returnsEmptyListWhenNoTasksExist() {

        TaskRepository repository = mock(TaskRepository.class);
        when(repository.findAll()).thenReturn(List.of());

        TaskUseCaseMapper mapper = mock(TaskUseCaseMapper.class);

        ListTasksUseCase useCase = TaskUseCasesFactory.listTasksUseCase(repository, mapper);

        List<TaskOutput> outputs = useCase.execute();

        assertThat(outputs).isEmpty();

        verify(repository).findAll();
        verify(mapper, never()).toTaskOutput(any(Task.class));
    }
}
