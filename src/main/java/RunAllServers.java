import DataServer.DataServerApplication;
import org.springframework.boot.SpringApplication;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.controller.rabbitMQ.RabbitMQLauncher;

import java.io.IOException;

public class RunAllServers
{
  // TODO: Change the executable location below, to the actual location of the executable that launches to RabbitMQ Service/Server on this local machine.
  public static final String RABBITMQ_EXECUTABLE_LOCATION = "C:\\Users\\krige\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\RabbitMQ Server\\RabbitMQ Service - start.lnk";

  // TODO: Set the proper address and port for the RabbitMQ server below:
  public static final String RABBITMQ_HOST_ADDRESS = "localhost";
  public static final int RABBITMQ_PORT = 5672;


  public static void main(String[] args) {
    // Start the Data Server:
    Thread DataServerThread = new Thread(() -> SpringApplication.run(DataServerApplication.class, args));
    DataServerThread.setDaemon(true);
    DataServerThread.start();

    // Start the RabbitMq Server:
    Thread DataBrokerServerThread = new Thread(() -> {
      // Check if a server is already running:
      RabbitMQChecker checker = new RabbitMQChecker(RABBITMQ_HOST_ADDRESS, RABBITMQ_PORT);

      if(!checker.isRabbitMQRunning()){
        // Server is NOT running. Launch the RabbitMQ Server:
        RabbitMQLauncher launcher = new RabbitMQLauncher(RABBITMQ_EXECUTABLE_LOCATION);
        try {
          launcher.startRabbitMQ();
        } catch (IOException | InterruptedException e) {
          e.printStackTrace(); // TODO: Improve exception handling.
        }
      }
    });
    DataBrokerServerThread.setDaemon(true);
    DataBrokerServerThread.start();

    // Wait for threads to end, before closing the application:
    try {
      DataServerThread.join();
      DataBrokerServerThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace(); // TODO: Improve exception handling.
    }
  }
}
