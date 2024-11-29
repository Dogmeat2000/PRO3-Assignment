package Client.Station1_AnimalRegistration;

import Client.Station1_AnimalRegistration.model.ProducedAnimalsQueueManager;
import Client.Station1_AnimalRegistration.model.Station1Model;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationServiceImpl;
import Client.Station1_AnimalRegistration.view.Station1View;
import Client.Station1_AnimalRegistration.viewModel.Station1ViewModel;
import Client.common.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;

public class RunStation1CLI
{
  public static void main(String[] args) {
    // Create the dependencies:
    String amqpHost = "localhost";
    int amqpPort = 5672;
    String exchangeName = "SlaughterHouse";
    String queueName = "QAni";
    String routingKeyWord = "Animal";

    BasicProducer amqpAnimalProducer = new BasicProducer(exchangeName,
        queueName,
        routingKeyWord,
        amqpHost,
        amqpPort);

    RabbitMQChecker rabbitMQChecker = new RabbitMQChecker("localhost", 5672);
    ProducedAnimalsQueueManager queueManager = new ProducedAnimalsQueueManager(amqpAnimalProducer, rabbitMQChecker);
    AnimalRegistrationService animalRegistrationService = new AnimalRegistrationServiceImpl("localhost", 9090);

    Station1Model station1Model = new Station1Model(animalRegistrationService, queueManager);
    Station1ViewModel viewModel = new Station1ViewModel(station1Model);
    Station1View view = new Station1View(viewModel);

    // Boot up the QueueManager:
    Thread queueManagerThread = new Thread(queueManager);
    queueManagerThread.setDaemon(true);
    queueManagerThread.start();

    // Launch Station 1 CLI:
    Thread Station1CLIThread = new Thread(() -> {
      view.run();
    });
    Station1CLIThread.start();
  }
}
