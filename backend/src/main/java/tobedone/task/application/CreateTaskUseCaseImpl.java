package tobedone.task.application;

import lombok.RequiredArgsConstructor;

import tobedone.task.application.dto.CreateTaskInput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.port.incoming.CreateTaskUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.domain.Task;
import tobedone.task.application.port.outgoing.TaskRepository;

@RequiredArgsConstructor
class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskUseCaseMapper mapper;

    @Override
    public CreateTaskOutput execute(CreateTaskInput input) {
        Task task = Task.create(input.title());
        var result = taskRepository.save(task);
        return mapper.toCreateTaskOutput(result);
    }
}

