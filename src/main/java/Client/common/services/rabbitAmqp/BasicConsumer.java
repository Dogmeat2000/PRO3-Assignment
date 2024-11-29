package Client.common.services.rabbitAmqp;

import Client.model.BaseModel;
import Client.Station2_Dissection.model.Station2Model;
import Client.Station3_Packing.model.Station3Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class BasicConsumer implements Runnable
{
  private final String EXCHANGE_NAME;
  private final String QUEUE_NAME;
  private final String ROUTING_KEY;
  private final ObjectMapper mapper = new ObjectMapper();
  private final RabbitMQChecker rabbitMQChecker;

  //@Value("${spring.rabbitmq.host:localhost}")
  private String rabbitMQServerAddress;

  //@Value("${spring.rabbitmq.port:5672}")
  private int rabbitMQServerPort;

  private final BaseModel model;

  public BasicConsumer(String exchangeName, String queueName, String routingKey, String rabbitMQServerAddress, int rabbitMQServerPort, BaseModel model, RabbitMQChecker rabbitMQChecker){
    this.rabbitMQServerAddress = rabbitMQServerAddress;
    this.rabbitMQServerPort = rabbitMQServerPort;
    this.EXCHANGE_NAME = exchangeName;
    this.QUEUE_NAME = queueName;
    this.ROUTING_KEY = routingKey;
    this.model = model;
    this.rabbitMQChecker = rabbitMQChecker;
  }

  //@PostConstruct
  @Override public void run() {

    // Set the configuration for this rabbitMq controller:
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(rabbitMQServerAddress);
    factory.setPort(rabbitMQServerPort);

    boolean amqpServerStated = false;

    // Check if connection can be established with a RabbitMQ server:
    while(!amqpServerStated){
      if(!rabbitMQChecker.isRabbitMQRunning()){
        System.err.println("[BasicConsumer] Critical Error: Could not connect to RabbitMQ");
        System.err.println("[BasicConsumer] Retrying in 5s...");
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
      } else {
        amqpServerStated = true;
      }
    }

    // Ensure this controller is launched inside a new Thread, to not halt the rest of the spring application:
    Thread consumerThread = new Thread(() -> {

      // Build the rabbitMq channel/connection:
      // Try-with-resources is used.
      try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

        // Declare the queue (in case it doesn't exist)
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // Durability is set to true, to ensure that data is not lost when the RabbitMQ server shuts down.
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // Bind the Queue to the exchange (in case it isn't already):
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        System.out.println("\n\n[BasicConsumer] Waiting for AMQP messages. To exit, stop the application.");

        // Define the consumer
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
          System.out.println("[BasicConsumer] AMQP Received '" + message + "'");

          try {
            // Process the message:
            processMessage(message);

            // Confirm message was processed, back to the publisher:
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

          }
          catch (Exception e) {
            // Return not-acknowledged to inform sender that the message was not properly received:
            System.err.println("\n[BasicConsumer] Error processing AMQP message: " + e.getMessage());
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
          }
        };

        // Handle cancellation of the consumer
        CancelCallback cancelCallback = consumerTag -> {
          System.out.println("\n[BasicConsumer] AMQP Consumer canceled: " + consumerTag);
        };

        // Start consuming messages
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);

        // Keep the thread alive to listen for messages
        Thread.currentThread().join();

      }
      catch (IOException | TimeoutException | InterruptedException e) {
        e.printStackTrace();
      }

    });

    // Start the thread:
    consumerThread.setDaemon(true);
    consumerThread.start();
  }

  private void processMessage(String message) {
    //System.out.println("[BasicConsumer] Processing AMQP message: " + message);

    try {
      // Deserialize JSON back into a DTO
      if(model instanceof Station2Model){
        // Deserialize into AnimalDto:
        AnimalDto dto = mapper.readValue(message, AnimalDto.class);

        // Add to Station 2's list of received Animals:
        model.addEntityToReceivedEntityList(dto);

      } else if (model instanceof Station3Model){
        // Deserialize into AnimalPart:
        AnimalPartDto dto = mapper.readValue(message, AnimalPartDto.class);

        // Add to Station 3's list of received AnimalParts:
        model.addEntityToReceivedEntityList(dto);
        System.out.println("[BasicConsumer] Finished processing AMQP message: " + message);

      } else {
        System.err.println("\n[BasicConsumer] Failed to deserialize received AMQP message. Did not match any station requirements.");
      }
    } catch (IOException e) {
      //e.printStackTrace();
      System.err.println("\n[BasicConsumer] Failed to deserialize received AMQP message. Received incompatible DTO object. Reason: " + e.getMessage());
    }
  }
}
