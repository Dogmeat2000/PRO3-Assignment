package shared.controller.rabbitMQ;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQLauncher
{
  @Value("${local.rabbitmq.executableLocation}")
  private final String rabbitMQServerExecutableLocation;

  public RabbitMQLauncher(String rabbitMQServerExecutableLocation) {
    this.rabbitMQServerExecutableLocation = rabbitMQServerExecutableLocation;
  }

  public void startRabbitMQ() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", rabbitMQServerExecutableLocation);
    processBuilder.redirectErrorStream(true);
    processBuilder.inheritIO();
    Process process = processBuilder.start();

    // Wait for RabbitMQ to be ready
    System.out.println("RabbitMQ server started. Waiting for it to initialize...");
    Thread.sleep(5000);
    System.out.println("RabbitMQ server started.");

    // Ensure the RabbitMQ server is shut down, when the creating thread also closes:
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (process.isAlive()) {
        System.out.println("Shutting down RabbitMQ server...");
        process.destroyForcibly();
      }
    }));

    process.waitFor();
  }
}
