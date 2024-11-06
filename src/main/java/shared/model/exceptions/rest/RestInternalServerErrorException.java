package shared.model.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestInternalServerErrorException extends RuntimeException
{
  public RestInternalServerErrorException(String message) {
    // Avoid exposing Internal Server Information to the user. Just return a generic error.
    super("An internal error occurred");
  }
}
