package tobedone.task.infrastructure.usecase.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tobedone.task.application.dto.CompleteTaskInput;
import tobedone.task.application.dto.CompleteTaskOutput;
import tobedone.task.application.port.incoming.CompleteTaskUseCase;

@RequiredArgsConstructor
public class TransactionalCompleteTaskUseCaseFacade implements CompleteTaskUseCase {

    private final CompleteTaskUseCase delegate;

    @Override
    @Transactional
    public CompleteTaskOutput execute(CompleteTaskInput input) {
        return delegate.execute(input);
    }
}
