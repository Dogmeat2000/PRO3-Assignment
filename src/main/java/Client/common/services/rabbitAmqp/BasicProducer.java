package Client.common.services.rabbitAmqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;

public class BasicProducer
{
  private final String EXCHANGE_NAME;
  private final String QUEUE_NAME;
  private final String ROUTING_KEY;
  private final String HOST;
  private final int PORT;
  private final ObjectMapper mapper = new ObjectMapper();

  public BasicProducer(String queueName, String exchangeName, String routingKey, String host, int port) {
    EXCHANGE_NAME = exchangeName;
    QUEUE_NAME = queueName;
    ROUTING_KEY = routingKey;
    HOST = host;
    PORT = port;
  }

  public boolean sendMessage(Object dto) {
    // Configure the connection:
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(HOST);
    factory.setPort(PORT);

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

      // Declare the queue (in case it doesn't exist):
      channel.exchangeDeclare(EXCHANGE_NAME, "direct");

      // Durability is set to true, to ensure that data is not lost when the RabbitMQ server shuts down.
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);

      // Bind the Queue to the exchange (in case it isn't already):
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

      // Serialize the DTO to JSON, before sending:
      String jsonMessage;
      if(dto instanceof AnimalDto){
        jsonMessage = mapper.writeValueAsString((AnimalDto) dto);
      } else if (dto instanceof AnimalPartDto){
        jsonMessage = mapper.writeValueAsString((AnimalPartDto) dto);
      } else {
        throw new DataIntegrityViolationException("[BasicProducer] Could not parse to json. Non-recognized class received.");
      }

      // Ensure messages are persisted:
      AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
          .deliveryMode(2) // Persistent
          .build();

      // Publish the message:
      channel.confirmSelect();
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, basicProperties, jsonMessage.getBytes());
      System.out.println("[BasicProducer] Sent AMQP Message:" + jsonMessage);

      if (!channel.waitForConfirms()) {
        System.err.println("[BasicProducer] AMQP Message delivery failed.");
        return false;
      }

      return true;

    } catch (Exception e) {
      e.printStackTrace(); // TODO: Improve exception handling
      return false;
    }
  }
}
