package tobedone.task.infrastructure.repository.jpa;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tobedone.task.domain.TaskStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {

    @Id
    private UUID id;

    private String title;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Instant createdAt;

    private Instant completedAt;
}

