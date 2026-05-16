package tobedone.task.application;

import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.domain.Task;

class TaskUseCaseMapperImpl implements TaskUseCaseMapper {

    @Override
    public CompleteTaskOutput toCompleteTaskOutput(Task task) {
        return new CompleteTaskOutput(task.getId(), task.getCompletedAt());
    }

    @Override
    public CreateTaskOutput toCreateTaskOutput(Task task) {
        return new CreateTaskOutput(
                task.getId(),
                task.getTitle(),
                task.getStatus().getDescription(),
                task.getCreatedAt(),
                task.getCompletedAt()
        );
    }

    @Override
    public TaskOutput toTaskOutput(Task task) {
        return new TaskOutput(
                task.getId(),
                task.getTitle(),
                task.getStatus().getDescription(),
                task.getCreatedAt(),
                task.getCompletedAt()
        );
    }
}
