package shared.model.exceptions.persistance;

public class CreateFailedException extends RuntimeException
{
  public CreateFailedException(String message) {
    super(message);
  }
}
