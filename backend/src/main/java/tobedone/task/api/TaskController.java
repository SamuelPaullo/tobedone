package tobedone.task.api;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tobedone.task.api.dto.CompleteTaskResponse;
import tobedone.task.api.dto.CreateTaskRequest;
import tobedone.task.api.dto.TaskResponse;
import tobedone.task.application.port.input.CompleteTaskUseCase;
import tobedone.task.application.port.input.CreateTaskUseCase;
import tobedone.task.application.port.input.ListTasksUseCase;

@RestController
@RequestMapping("/tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

	private final CreateTaskUseCase createTaskUseCase;
	private final ListTasksUseCase listTasksUseCase;
	private final CompleteTaskUseCase completeTaskUseCase;
	private final TaskApiMapper mapper;

	@PostMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
		var output = createTaskUseCase.execute(request.title());
		return mapper.toResponse(output);
	}

	@GetMapping
	public List<TaskResponse> list() {
		return listTasksUseCase.execute().stream().map(mapper::toResponse).toList();
	}

	@PatchMapping("/{id}/complete")
	public CompleteTaskResponse complete(@PathVariable UUID id) {
		var output = completeTaskUseCase.execute(id);
		return mapper.toResponse(output);
	}
}
