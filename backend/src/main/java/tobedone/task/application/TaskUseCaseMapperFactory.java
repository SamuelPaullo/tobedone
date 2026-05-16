package tobedone.task.application;

import tobedone.task.application.port.input.TaskUseCaseMapper;

public final class TaskUseCaseMapperFactory {

    private TaskUseCaseMapperFactory() {
    }

    public static TaskUseCaseMapper taskUseCaseMapper() {
        return new TaskUseCaseMapperImpl();
    }
}
