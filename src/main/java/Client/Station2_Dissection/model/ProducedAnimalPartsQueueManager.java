package Client.Station2_Dissection.model;

import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.common.model.QueueManager;
import Client.common.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.PartTypeDto;
import shared.model.dto.TrayDto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducedAnimalPartsQueueManager implements QueueManager
{
  private final Queue<AnimalPartDto> unregisteredAnimalPartQueue = new LinkedList<>();
  private final Queue<AnimalPartDto> registeredAnimalPartQueue = new LinkedList<>();
  private final BasicProducer animalPartProducer;
  private final RabbitMQChecker rabbitMQChecker;
  private final AnimalPartRegistrationService animalPartRegistrationService;

  public ProducedAnimalPartsQueueManager(BasicProducer animalPartProducer, RabbitMQChecker rabbitMQChecker, AnimalPartRegistrationService animalPartRegistrationService){
    this.animalPartProducer = animalPartProducer;
    this.rabbitMQChecker = rabbitMQChecker;
    this.animalPartRegistrationService = animalPartRegistrationService;
  }

  @Override public void run() {
    // Handle the unregistered Animals on its own thread:
    Thread handleUnregisteredAnimalPartsThread = new Thread(this::handleUnregisteredAnimalParts);
    handleUnregisteredAnimalPartsThread.setDaemon(true);
    handleUnregisteredAnimalPartsThread.start();

    // Handle the registered Animals on its own thread:
    Thread handleregisteredAnimalPartsThread = new Thread(this::handleRegisteredAnimalParts);
    handleregisteredAnimalPartsThread.setDaemon(true);
    handleregisteredAnimalPartsThread.start();

    // Wait for these two threads to close:
    try {
      handleUnregisteredAnimalPartsThread.join();
      handleregisteredAnimalPartsThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleUnregisteredAnimalParts(){
    // Run continuously for as long as this thread lives:
    while(true){
      // Check if Queue is non-empty:
      if(!unregisteredAnimalPartQueue.isEmpty()){
        // Extract data:
        AnimalPartDto animalPart = unregisteredAnimalPartQueue.poll();
        try {
          AnimalDto animal = new AnimalDto();
          animal.setAnimalId(animalPart.getAnimalId());

          PartTypeDto type = new PartTypeDto(0, "");
          type.setTypeId(animalPart.getTypeId());

          TrayDto tray = new TrayDto(0, BigDecimal.ZERO, BigDecimal.ZERO, 0, null);
          tray.setTrayId(animalPart.getTrayId());

          BigDecimal weightInKilogram = animalPart.getWeight_kilogram();

          // Attempt to register next Animal in the Queue, using gRPC connection:
          AnimalPartDto registeredAnimalPart = animalPartRegistrationService.registerNewAnimalPart(animal, type, tray, weightInKilogram);
          System.out.println("\n[QueueManager] Saved animalPart {" + registeredAnimalPart + "} to database.");

          // Add the registered entity to the proper Queue for indirect transmission to the next station:
          if(!this.addToRegisteredQueue(registeredAnimalPart)){
            // Registered entity was not properly added to the queue. Throw an exception.
            throw new RuntimeException("Critical Failure: Failed to add the registered AnimalPart to the queue, before transmission via RabbitMq.");
          }
        } catch (Exception e) {
          System.err.println("\n[QueueManager] Failed to register animalPart {" + animalPart + "}. Reason: " + e.getMessage());
          System.err.println("[QueueManager] Adding failed AnimalPart to the end of the queue, and trying next AnimalPart in queue.");
          unregisteredAnimalPartQueue.offer(animalPart);
          try {
            Thread.sleep(250);
          } catch (InterruptedException ex) {
            e.printStackTrace();
          }
        }

      } else {
        // Queue is empty. Wait a bit, before checking again:
        try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void handleRegisteredAnimalParts(){
    // Run continuously for as long as this thread lives:
    while(true){
      try {
        // Check if connection can be established with a RabbitMQ server:
        if(!rabbitMQChecker.isRabbitMQRunning()){
          System.err.println("[ProducedAnimalsQueueManager] Critical Error: Could not connect to RabbitMQ");
          System.err.println("[ProducedAnimalsQueueManager] Retrying in 5s...");
          Thread.sleep(5000);

        } else {
          // Try to retrieve an Entity from the Queue:
          try {
            AnimalPartDto animalPart = removeFirst();

            // Try to send this Entity, to the RabbitMq server:
            boolean success = animalPartProducer.sendMessage(animalPart);

            // If the RabbitMQ server did receive the message, re add the AnimalDto to the queue.
            if(!success){
              addToRegisteredQueue(animalPart);
            }

          } catch (NullPointerException e) {
            // Queue is empty. Sleep a bit, and check again:
            Thread.sleep(1000);
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override public boolean addToUnregisteredQueue(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalPartDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalPartDto");

    return unregisteredAnimalPartQueue.offer((AnimalPartDto) obj);
  }

  private boolean addToRegisteredQueue(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalPartDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalPartDto");

    return registeredAnimalPartQueue.offer((AnimalPartDto) obj);
  }

  @Override public List<Object> copyRegisteredQueue() {
    return new LinkedList<>(registeredAnimalPartQueue);
  }

  private AnimalPartDto removeFirst() {
    AnimalPartDto animalPart = registeredAnimalPartQueue.poll();
    if(animalPart == null)
      throw new NullPointerException("Queue is empty");
    else
      return animalPart;
  }
}
