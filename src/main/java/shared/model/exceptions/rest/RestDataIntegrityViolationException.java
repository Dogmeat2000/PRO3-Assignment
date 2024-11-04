package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RestDataIntegrityViolationException extends RuntimeException
{
  public RestDataIntegrityViolationException(String message) {
    super(message);
  }
}
