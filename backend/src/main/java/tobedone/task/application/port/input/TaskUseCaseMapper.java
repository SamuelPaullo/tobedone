package tobedone.task.application.port.input;

import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.domain.Task;

public interface TaskUseCaseMapper {

    CompleteTaskOutput toCompleteTaskOutput(Task task);

    CreateTaskOutput toCreateTaskOutput(Task task);

    TaskOutput toTaskOutput(Task task);
}
