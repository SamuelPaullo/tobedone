package tobedone.task.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.domain.Task;
import tobedone.task.application.port.output.TaskRepository;

@Service
@AllArgsConstructor
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

