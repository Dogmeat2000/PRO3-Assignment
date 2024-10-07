package shared.model.exceptions;

public class DeleteFailedException extends RuntimeException
{
  public DeleteFailedException(String message) {
    super(message);
  }
}
