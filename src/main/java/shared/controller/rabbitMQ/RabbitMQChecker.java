package shared.controller.rabbitMQ;

import java.net.Socket;

public class RabbitMQChecker
{
  private final String host;
  private final int port;

  public RabbitMQChecker(String host, int port){
    this.host = host;
    this.port = port;
  }

  public boolean isRabbitMQRunning() {
    // Attempt to establish connection to local RabbitMQ Server:
    try (Socket socket = new Socket(host, port)) {
      // Connection successful, RabbitMQ is running:
      //System.out.println("RabbitMQ is already running...");
      return true;

    } catch (Exception e) {
      // Connection failed, RabbitMQ is not running:
      System.err.println("RabbitMQ is not running...");
      return false;
    }
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port; }
}
