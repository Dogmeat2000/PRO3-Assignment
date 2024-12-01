package Client.Station3_Packing;

import Client.Station3_Packing.model.ProducedProductsQueueManager;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystem;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystemImpl;
import Client.Station3_Packing.view.Station3View;
import Client.Station3_Packing.viewModel.Station3ViewModel;
import Client.common.services.gRPC.PartTypeRegistrationSystem;
import Client.common.services.gRPC.PartTypeRegistrationSystemImpl;
import Client.common.services.gRPC.TrayRegistrationSystem;
import Client.common.services.gRPC.TrayRegistrationSystemImpl;
import Client.Station3_Packing.model.Station3Model;
import Client.common.services.rabbitAmqp.BasicConsumer;
import shared.controller.rabbitMQ.RabbitMQChecker;

public class RunStation3CLI
{
  public static void main(String[] args) {
    // Create the dependencies:
    String amqpHost = "localhost";
    int amqpPort = 5672;
    String exchangeName = "SlaughterHouse";
    String queueNameAnimalPartQueue = "QAniPart";
    String routingKeyWordAnimalPartQueue = "AnimalPart";

    ProductRegistrationSystem productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
    TrayRegistrationSystem trayRegistrationSystem = new TrayRegistrationSystemImpl("localhost", 9090);
    PartTypeRegistrationSystem partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl("localhost", 9090);
    RabbitMQChecker rabbitMQChecker = new RabbitMQChecker(amqpHost, amqpPort);
    ProducedProductsQueueManager queueManager = new ProducedProductsQueueManager();
    Station3Model station3Model = new Station3Model(productRegistrationSystem, queueManager, trayRegistrationSystem);
    BasicConsumer amqpAnimalPartConsumer = new BasicConsumer(exchangeName, queueNameAnimalPartQueue, routingKeyWordAnimalPartQueue, amqpHost, amqpPort, station3Model, rabbitMQChecker);

    Station3ViewModel viewModel = new Station3ViewModel(station3Model);
    Station3View view = new Station3View(viewModel);

    // Boot up the QueueManager:
    Thread queueManagerThread = new Thread(queueManager);
    queueManagerThread.setDaemon(true);
    queueManagerThread.start();

    // Boot up the AMQP Message Consumer:
    Thread consumerThread = new Thread(amqpAnimalPartConsumer);
    consumerThread.setDaemon(true);
    consumerThread.start();

    // Launch Station 3 CLI:
    Thread Station2CLIThread = new Thread(() -> {
      view.run();
    });
    Station2CLIThread.start();
  }
}
