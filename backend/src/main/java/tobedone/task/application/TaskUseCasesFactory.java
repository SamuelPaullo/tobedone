package tobedone.task.application;

import tobedone.task.application.port.incoming.CompleteTaskUseCase;
import tobedone.task.application.port.incoming.CreateTaskUseCase;
import tobedone.task.application.port.incoming.ListTasksUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;

public final class TaskUseCasesFactory {

    private TaskUseCasesFactory() {
    }

    public static CreateTaskUseCase createTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new CreateTaskUseCaseImpl(repository, mapper);
    }

    public static ListTasksUseCase listTasksUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new ListTasksUseCaseImpl(repository, mapper);
    }

    public static CompleteTaskUseCase completeTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new CompleteTaskUseCaseImpl(repository, mapper);
    }
}
