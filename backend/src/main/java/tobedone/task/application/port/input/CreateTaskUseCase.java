package tobedone.task.application.port.input;

import tobedone.task.application.dto.CreateTaskOutput;

public interface CreateTaskUseCase {

    CreateTaskOutput execute(String title);
}
