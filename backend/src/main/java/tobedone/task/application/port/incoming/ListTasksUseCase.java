package tobedone.task.application.port.incoming;

import java.util.List;

import tobedone.task.application.dto.TaskOutput;

public interface ListTasksUseCase {

	List<TaskOutput> execute();
}
