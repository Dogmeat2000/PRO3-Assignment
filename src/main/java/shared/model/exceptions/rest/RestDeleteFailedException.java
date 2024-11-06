package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RestDeleteFailedException extends RuntimeException {
  public RestDeleteFailedException(String message) {
    super(message);
  }
}
