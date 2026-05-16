package tobedone.task.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import tobedone.task.domain.Task;
import tobedone.task.application.port.output.TaskRepository;
import tobedone.task.infrastructure.persistence.jpa.TaskPersistenceMapper;
import tobedone.task.infrastructure.persistence.jpa.SpringDataTaskJpaRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

	private final SpringDataTaskJpaRepository jpaRepository;
	private final TaskPersistenceMapper mapper;

	public TaskRepositoryImpl(SpringDataTaskJpaRepository jpaRepository, TaskPersistenceMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Task save(Task task) {
		return mapper.toDomain(jpaRepository.save(mapper.toEntity(task)));
	}

	@Override
	public List<Task> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public Optional<Task> findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}
}

