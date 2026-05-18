package tobedone.task.application;

import lombok.RequiredArgsConstructor;
import tobedone.task.application.dto.CompleteTaskInput;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.port.incoming.CompleteTaskUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.application.exception.TaskNotFoundException;

@RequiredArgsConstructor
class CompleteTaskUseCaseImpl implements CompleteTaskUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public CompleteTaskOutput execute(CompleteTaskInput input) {
		var taskId = input.taskId();
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
		task.markAsComplete();
		var result =  taskRepository.save(task);
		return mapper.toCompleteTaskOutput(result);
	}
}
