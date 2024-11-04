package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DtoConversionException extends RuntimeException
{
  public DtoConversionException(String message) {
    super(message);
  }
}
