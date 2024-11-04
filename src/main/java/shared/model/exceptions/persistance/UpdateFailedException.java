package shared.model.exceptions.persistance;

public class UpdateFailedException extends RuntimeException
{
  public UpdateFailedException(String message) {
    super(message);
  }
}
