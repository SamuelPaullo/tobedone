package tobedone.task.application.port.input;

import tobedone.task.application.dto.CompleteTaskOutput;

import java.util.UUID;

public interface CompleteTaskUseCase {

    CompleteTaskOutput execute(UUID id);
}
