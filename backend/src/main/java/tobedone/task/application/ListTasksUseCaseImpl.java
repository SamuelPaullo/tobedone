package tobedone.task.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.input.ListTasksUseCase;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.application.port.output.TaskRepository;

@RequiredArgsConstructor
class ListTasksUseCaseImpl implements ListTasksUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public List<TaskOutput> execute() {
		return taskRepository.findAll().stream().map(mapper::toTaskOutput).toList();
	}
}
