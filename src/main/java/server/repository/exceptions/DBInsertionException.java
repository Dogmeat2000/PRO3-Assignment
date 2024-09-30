package server.repository.exceptions;

public class DBInsertionException extends RuntimeException
{
  public DBInsertionException(String message) {
    super(message);
  }
}
