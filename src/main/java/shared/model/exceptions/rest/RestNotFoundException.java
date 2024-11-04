package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestNotFoundException extends RuntimeException {
  public RestNotFoundException(String message) {
    super(message);
  }
}
