package tobedone.task.application;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.input.ListTasksUseCase;
import tobedone.task.application.port.output.TaskRepository;

@Service
@AllArgsConstructor
class ListTasksUseCaseImpl implements ListTasksUseCase {

	private final TaskRepository taskRepository;
	private final TaskUseCaseMapper mapper;

	@Override
	public List<TaskOutput> execute() {
		return taskRepository.findAll().stream().map(mapper::toTaskOutput).toList();
	}
}
