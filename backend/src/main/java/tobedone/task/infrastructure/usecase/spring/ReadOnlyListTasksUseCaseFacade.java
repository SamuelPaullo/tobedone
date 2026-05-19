package tobedone.task.infrastructure.usecase.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tobedone.task.application.dto.TaskOutput;
import tobedone.task.application.port.incoming.ListTasksUseCase;

import java.util.List;

@RequiredArgsConstructor
public class ReadOnlyListTasksUseCaseFacade implements ListTasksUseCase {

    private final ListTasksUseCase delegate;

    @Override
    @Transactional(readOnly = true)
    public List<TaskOutput> execute() {
        return delegate.execute();
    }
}
