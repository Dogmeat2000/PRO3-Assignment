package server.repository.exceptions;

public class DBPrimaryKeyRetrievalException extends RuntimeException
{
  public DBPrimaryKeyRetrievalException(String message) {
    super(message);
  }
}
