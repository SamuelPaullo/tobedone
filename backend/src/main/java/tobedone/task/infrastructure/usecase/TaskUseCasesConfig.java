package tobedone.task.infrastructure.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobedone.task.application.TaskUseCaseMapperFactory;
import tobedone.task.application.TaskUseCaseFactory;
import tobedone.task.application.port.incoming.CompleteTaskUseCase;
import tobedone.task.application.port.incoming.CreateTaskUseCase;
import tobedone.task.application.port.incoming.ListTasksUseCase;
import tobedone.task.application.port.incoming.TaskUseCaseMapper;
import tobedone.task.application.port.outgoing.TaskRepository;
import tobedone.task.infrastructure.usecase.spring.ReadOnlyListTasksUseCaseFacade;
import tobedone.task.infrastructure.usecase.spring.TransactionalCompleteTaskUseCaseFacade;
import tobedone.task.infrastructure.usecase.spring.TransactionalCreateTaskUseCaseFacade;

@Configuration
class TaskUseCasesConfig {

    @Bean
    TaskUseCaseMapper taskUseCaseMapper() {
        return TaskUseCaseMapperFactory.taskUseCaseMapper();
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new TransactionalCreateTaskUseCaseFacade(
                TaskUseCaseFactory.createTaskUseCase(repository, mapper)
        );
    }

    @Bean
    ListTasksUseCase listTasksUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new ReadOnlyListTasksUseCaseFacade(
                TaskUseCaseFactory.listTasksUseCase(repository, mapper)
        );
    }

    @Bean
    CompleteTaskUseCase completeTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return new TransactionalCompleteTaskUseCaseFacade(
                TaskUseCaseFactory.completeTaskUseCase(repository, mapper)
        );
    }
}
