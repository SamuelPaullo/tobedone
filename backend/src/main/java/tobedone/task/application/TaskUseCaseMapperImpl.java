package tobedone.task.application;

import org.springframework.stereotype.Component;

import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.domain.Task;

class TaskUseCaseMapper {

    CompleteTaskOutput toCompleteTaskOutput(Task task) {
        return new CompleteTaskOutput(task.getId(), task.getCompletedAt());
    }

    CreateTaskOutput toCreateTaskOutput(Task task) {
        return new CreateTaskOutput(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getCompletedAt()
        );
    }

    TaskOutput toTaskOutput(Task task) {
        return new TaskOutput(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getCompletedAt()
        );
    }
}
