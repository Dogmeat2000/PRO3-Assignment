package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "shared.model.entities")
//@ComponentScan(basePackages = "client.ui.Model.service")
public class ServerApplication
{
  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}
