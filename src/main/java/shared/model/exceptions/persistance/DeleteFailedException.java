package shared.model.exceptions.persistance;

public class DeleteFailedException extends RuntimeException
{
  public DeleteFailedException(String message) {
    super(message);
  }
}
