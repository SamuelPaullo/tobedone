package tobedone.task.application;

import lombok.RequiredArgsConstructor;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.port.input.CompleteTaskUseCase;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.application.port.output.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.domain.exception.TaskNotFoundException;

import java.util.UUID;

@RequiredArgsConstructor
class CompleteTaskUseCaseImpl implements CompleteTaskUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public CompleteTaskOutput execute(UUID id) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
		task.markAsComplete();
		var result =  taskRepository.save(task);
		return mapper.toCompleteTaskOutput(result);
	}
}
