package tobedone.task.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(@NotBlank String title) {
}

