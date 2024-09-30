package server.repository.exceptions;

public class DBPrimaryKeyMatchNotFound extends RuntimeException
{
  public DBPrimaryKeyMatchNotFound(String message) {
    super(message);
  }
}
