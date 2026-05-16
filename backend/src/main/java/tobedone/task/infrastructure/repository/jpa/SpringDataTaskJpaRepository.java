package tobedone.task.infrastructure.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTaskJpaRepository extends JpaRepository<TaskEntity, UUID> {
}

