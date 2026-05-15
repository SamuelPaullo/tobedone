package tobedone.task.api;

import org.springframework.stereotype.Component;

import tobedone.task.api.dto.CompleteTaskResponse;
import tobedone.task.api.dto.TaskResponse;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.dto.TaskOutput;

@Component
public class TaskApiMapper {

	public CompleteTaskResponse toResponse(CompleteTaskOutput output) {
		return new CompleteTaskResponse(output.taskId(), output.completedAt());
	}

	public TaskResponse toResponse(CreateTaskOutput output) {
		return new TaskResponse(
			output.id(),
			output.title(),
			output.status(),
			output.createdAt(),
			output.completedAt()
		);
	}

	public TaskResponse toResponse(TaskOutput output) {
		return new TaskResponse(
			output.id(),
			output.title(),
			output.status(),
			output.createdAt(),
			output.completedAt()
		);
	}
}
