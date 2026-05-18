package tobedone.task.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.incoming.ListTasksUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;

@RequiredArgsConstructor
class ListTasksUseCaseImpl implements ListTasksUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public List<TaskOutput> execute() {
		return taskRepository.findAll().stream().map(mapper::toTaskOutput).toList();
	}
}
