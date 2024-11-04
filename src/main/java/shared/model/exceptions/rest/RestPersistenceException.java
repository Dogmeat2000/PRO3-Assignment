package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestPersistenceException extends RuntimeException
{
  public RestPersistenceException(String message) {
    super(message);
  }
}
