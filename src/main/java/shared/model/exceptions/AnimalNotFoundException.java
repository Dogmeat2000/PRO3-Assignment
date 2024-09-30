package shared.model.exceptions;

public class AnimalNotFoundException extends RuntimeException
{
  public AnimalNotFoundException(String message) {
    super(message);
  }
}
