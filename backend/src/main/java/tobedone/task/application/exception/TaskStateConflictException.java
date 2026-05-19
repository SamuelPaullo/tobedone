package tobedone.task.application.exception;

public class TaskStateConflictException extends RuntimeException {

    public TaskStateConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}

