package tobedone.task.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobedone.task.application.TaskUseCaseMapperFactory;
import tobedone.task.application.TaskUseCasesFactory;
import tobedone.task.application.port.input.CompleteTaskUseCase;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.application.port.input.ListTasksUseCase;
import tobedone.task.application.port.input.TaskUseCaseMapper;
import tobedone.task.application.port.output.TaskRepository;

@Configuration
class TaskUseCasesConfig {

    @Bean
    TaskUseCaseMapper taskUseCaseMapper() {
        return TaskUseCaseMapperFactory.taskUseCaseMapper();
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return TaskUseCasesFactory.createTaskUseCase(repository, mapper);
    }

    @Bean
    ListTasksUseCase listTasksUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return TaskUseCasesFactory.listTasksUseCase(repository, mapper);
    }

    @Bean
    CompleteTaskUseCase completeTaskUseCase(TaskRepository repository, TaskUseCaseMapper mapper) {
        return TaskUseCasesFactory.completeTaskUseCase(repository, mapper);
    }
}
