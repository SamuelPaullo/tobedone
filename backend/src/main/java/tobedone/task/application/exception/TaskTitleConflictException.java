package tobedone.task.application.exception;

public class TaskTitleConflictException extends RuntimeException {
    public TaskTitleConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
