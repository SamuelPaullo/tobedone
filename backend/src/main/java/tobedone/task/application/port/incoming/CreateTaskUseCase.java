package tobedone.task.application.port.incoming;

import tobedone.task.application.dto.CreateTaskInput;
import tobedone.task.application.dto.CreateTaskOutput;

public interface CreateTaskUseCase {

    CreateTaskOutput execute(CreateTaskInput input);
}
