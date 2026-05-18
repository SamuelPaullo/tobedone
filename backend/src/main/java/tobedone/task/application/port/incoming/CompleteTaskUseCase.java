package tobedone.task.application.port.incoming;

import tobedone.task.application.dto.CompleteTaskInput;
import tobedone.task.application.dto.CompleteTaskOutput;

public interface CompleteTaskUseCase {

    CompleteTaskOutput execute(CompleteTaskInput input);
}
