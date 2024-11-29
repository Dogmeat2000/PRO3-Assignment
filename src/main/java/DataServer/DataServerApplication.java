package DataServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "DataServer.model.persistence.entities")
public class DataServerApplication
{
  public static void main(String[] args) {
    // Launch the main Data server:
    SpringApplication.run(DataServerApplication.class, args);
  }
}
