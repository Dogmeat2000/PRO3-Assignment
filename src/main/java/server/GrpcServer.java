package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "shared.model.entities")
public class GrpcServer
{
  public static void main(String[] args) {
    SpringApplication.run(GrpcServer.class, args);
  }
}
