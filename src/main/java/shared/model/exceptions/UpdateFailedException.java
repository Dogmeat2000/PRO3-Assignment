package shared.model.exceptions;

public class UpdateFailedException extends RuntimeException
{
  public UpdateFailedException(String message) {
    super(message);
  }
}
