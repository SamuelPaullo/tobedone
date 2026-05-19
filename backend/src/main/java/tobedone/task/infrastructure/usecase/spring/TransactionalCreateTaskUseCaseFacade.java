package tobedone.task.infrastructure.usecase.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tobedone.task.application.dto.CreateTaskInput;
import tobedone.task.application.dto.CreateTaskOutput;
import tobedone.task.application.port.incoming.CreateTaskUseCase;

@RequiredArgsConstructor
public class TransactionalCreateTaskUseCaseFacade implements CreateTaskUseCase {

    private final CreateTaskUseCase delegate;

    @Override
    @Transactional
    public CreateTaskOutput execute(CreateTaskInput input) {
        return delegate.execute(input);
    }
}
