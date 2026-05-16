package tobedone.task.infrastructure.repository.jpa;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tobedone.task.domain.TaskStatus;

@Entity
@Table(name = "tasks")
public class TaskEntity {

	@Id
	private UUID id;

	private String title;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	private Instant createdAt;

	private Instant completedAt;

	protected TaskEntity() {
	}

	public TaskEntity(UUID id, String title, TaskStatus status, Instant createdAt, Instant completedAt) {
		this.id = id;
		this.title = title;
		this.status = status;
		this.createdAt = createdAt;
		this.completedAt = completedAt;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getCompletedAt() {
		return completedAt;
	}
}

