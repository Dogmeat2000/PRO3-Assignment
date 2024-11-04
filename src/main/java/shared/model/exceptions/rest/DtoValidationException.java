package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DtoValidationException extends RuntimeException
{
  public DtoValidationException(String message) {
    super(message);
  }
}
