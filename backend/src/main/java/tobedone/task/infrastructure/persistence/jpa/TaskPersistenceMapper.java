package tobedone.task.infrastructure.persistence.jpa;

import org.springframework.stereotype.Component;

import tobedone.task.domain.Task;

@Component
public class TaskPersistenceMapper {

	public TaskEntity toEntity(Task task) {
		return new TaskEntity(task.getId(), task.getTitle(), task.getStatus(), task.getCreatedAt(), task.getCompletedAt());
	}

	public Task toDomain(TaskEntity entity) {
		return new Task(entity.getId(), entity.getTitle(), entity.getStatus(), entity.getCreatedAt(), entity.getCompletedAt());
	}
}

