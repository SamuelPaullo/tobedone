package tobedone.task.application;

import lombok.RequiredArgsConstructor;
import tobedone.task.application.dto.CompleteTaskInput;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.exception.TaskNotFoundException;
import tobedone.task.application.exception.TaskStateConflictException;
import tobedone.task.application.port.incoming.CompleteTaskUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.domain.Task;
import tobedone.task.domain.exception.InvalidTaskStateException;

@RequiredArgsConstructor
class CompleteTaskUseCaseImpl implements CompleteTaskUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public CompleteTaskOutput execute(CompleteTaskInput input) {
		var taskId = input.taskId();
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
		try {
			task.markAsComplete();
		} catch (InvalidTaskStateException ex) {
			throw new TaskStateConflictException(ex.getMessage(), ex);
		}
		var result =  taskRepository.save(task);
		return mapper.toCompleteTaskOutput(result);
	}
}
