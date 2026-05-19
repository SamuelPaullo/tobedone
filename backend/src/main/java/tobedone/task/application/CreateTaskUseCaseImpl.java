package tobedone.task.application;

import lombok.RequiredArgsConstructor;

import tobedone.task.application.dto.CreateTaskInput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.exception.TaskStateConflictException;
import tobedone.task.application.exception.TaskTitleConflictException;
import tobedone.task.application.port.incoming.CreateTaskUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.domain.exception.InvalidTaskStateException;
import tobedone.task.domain.exception.InvalidTaskTitleException;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskUseCaseMapper mapper;

    @Override
    public CreateTaskOutput execute(CreateTaskInput input) {
        try {
            Task task = Task.create(
                    UUID.randomUUID(),
                    input.title(),
                    Instant.now()
            );
            var result = taskRepository.save(task);
            return mapper.toCreateTaskOutput(result);
        } catch (InvalidTaskStateException ex) {
            throw new TaskStateConflictException(ex.getMessage(), ex);
        } catch (InvalidTaskTitleException ex) {
            throw new TaskTitleConflictException(ex.getMessage(), ex);
        }
    }
}

