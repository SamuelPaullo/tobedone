package tobedone.task.api;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tobedone.task.application.exception.TaskNotFoundException;
import tobedone.task.application.exception.TaskStateConflictException;
import tobedone.task.application.exception.TaskTitleConflictException;

@RestControllerAdvice
class TaskExceptionHandler {

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError("TASK_NOT_FOUND", ex.getMessage(), Instant.now()));
	}

	@ExceptionHandler(TaskStateConflictException.class)
	public ResponseEntity<?> handleInvalidState(TaskStateConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiError("INVALID_TASK_STATE", ex.getMessage(), Instant.now()));
	}

	@ExceptionHandler(TaskTitleConflictException.class)
	public ResponseEntity<?> handleInvalidTitle(TaskTitleConflictException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError("INVALID_TASK_TITLE", ex.getMessage(), Instant.now()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError("VALIDATION_ERROR", "Request validation failed", Instant.now()));
	}

	record ApiError(String code, String message, Instant timestamp) {
	}
}

