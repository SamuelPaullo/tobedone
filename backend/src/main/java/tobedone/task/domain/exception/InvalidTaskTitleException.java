package tobedone.task.domain.exception;

public class InvalidTaskTitleException extends RuntimeException {

     public InvalidTaskTitleException(String message) {
         super(message);
     }
}
