package tobedone.task.application;

import tobedone.task.application.port.input.CompleteTaskUseCase;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.application.port.input.ListTasksUseCase;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.application.port.output.TaskRepository;

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
