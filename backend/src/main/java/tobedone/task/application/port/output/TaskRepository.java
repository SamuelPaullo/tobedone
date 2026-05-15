package tobedone.task.application.port.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import tobedone.task.domain.Task;

public interface TaskRepository {

	Task save(Task task);

	List<Task> findAll();

	Optional<Task> findById(UUID id);
}

