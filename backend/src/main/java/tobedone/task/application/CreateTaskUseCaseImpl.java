package tobedone.task.application;

import lombok.RequiredArgsConstructor;

import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.domain.Task;
import tobedone.task.application.port.output.TaskRepository;

@RequiredArgsConstructor
class CreateTaskUseCaseImpl implements CreateTaskUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public CreateTaskOutput execute(String title) {
		Task task = Task.create(title);
		var result = taskRepository.save(task);
		return mapper.toCreateTaskOutput(result);
	}
}

