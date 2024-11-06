package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RestUpdateFailedException extends RuntimeException
{
  public RestUpdateFailedException(String message) {
    super(message);
  }
}
