package shared.model.exceptions;

public class CreateFailedException extends RuntimeException
{
  public CreateFailedException(String message) {
    super(message);
  }
}
