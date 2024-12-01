package Client.Station2_Dissection;

import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationServiceImpl;
import Client.Station2_Dissection.view.Station2View;
import Client.Station2_Dissection.viewModel.Station2ViewModel;
import Client.common.services.gRPC.PartTypeRegistrationSystem;
import Client.common.services.gRPC.PartTypeRegistrationSystemImpl;
import Client.common.services.gRPC.TrayRegistrationSystem;
import Client.common.services.gRPC.TrayRegistrationSystemImpl;
import Client.Station2_Dissection.model.ProducedAnimalPartsQueueManager;
import Client.Station2_Dissection.model.Station2Model;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.common.services.rabbitAmqp.BasicConsumer;
import Client.common.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;

public class RunStation2CLI
{
  public static void main(String[] args) {
    System.out.println("\nSTATION 2: Animal Dissection (Command Line Interface)\nThis CLI is for debugging purposes!");

    // Create the dependencies:
    String amqpHost = "localhost";
    int amqpPort = 5672;
    String exchangeName = "SlaughterHouse";
    String queueNameAnimalQueue = "QAni";
    String routingKeyWordAnimalQueue =  "Animal";
    String queueNameAnimalPartQueue = "QAniPart";
    String routingKeyWordAnimalPartQueue = "AnimalPart";

    BasicProducer amqpAnimalPartProducer = new BasicProducer(exchangeName, queueNameAnimalPartQueue, routingKeyWordAnimalPartQueue, amqpHost, amqpPort);
    AnimalPartRegistrationService animalPartRegistrationService = new AnimalPartRegistrationServiceImpl("localhost", 9090);
    TrayRegistrationSystem trayRegistrationSystem = new TrayRegistrationSystemImpl("localhost", 9090);
    PartTypeRegistrationSystem partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl("localhost", 9090);
    RabbitMQChecker rabbitMQChecker = new RabbitMQChecker(amqpHost, amqpPort);
    ProducedAnimalPartsQueueManager queueManager = new ProducedAnimalPartsQueueManager(amqpAnimalPartProducer, rabbitMQChecker);
    Station2Model station2Model = new Station2Model(animalPartRegistrationService, queueManager, partTypeRegistrationSystem, trayRegistrationSystem);
    BasicConsumer amqpAnimalConsumer = new BasicConsumer(exchangeName, queueNameAnimalQueue, routingKeyWordAnimalQueue, amqpHost, amqpPort, station2Model, rabbitMQChecker);

    Station2ViewModel viewModel = new Station2ViewModel(station2Model);
    Station2View view = new Station2View(viewModel);

    // Boot up the QueueManager:
    Thread queueManagerThread = new Thread(queueManager);
    queueManagerThread.setDaemon(true);
    queueManagerThread.start();

    // Boot up the AMQP Message Consumer:
    Thread consumerThread = new Thread(amqpAnimalConsumer);
    consumerThread.setDaemon(true);
    consumerThread.start();

    // Launch Station 2 CLI:
    Thread Station2CLIThread = new Thread(() -> {
      view.run();
    });
    Station2CLIThread.start();
  }
}
