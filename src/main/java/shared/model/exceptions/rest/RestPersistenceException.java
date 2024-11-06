package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class RestPersistenceException extends RuntimeException
{
  public RestPersistenceException(String message) {
    // Avoid exposing Internal Server Information to the user. Just return a generic error.
    super(message);
  }
}
