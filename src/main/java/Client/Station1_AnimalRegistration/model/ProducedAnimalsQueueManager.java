package Client.Station1_AnimalRegistration.model;

import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.common.model.QueueManager;
import Client.common.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducedAnimalsQueueManager implements QueueManager
{
  private final Queue<AnimalDto> registeredAnimalQueue = new LinkedList<>();
  private final Queue<AnimalDto> unregisteredAnimalQueue = new LinkedList<>();
  private final BasicProducer animalProducer;
  private final RabbitMQChecker rabbitMQChecker;
  private final AnimalRegistrationService animalRegistrationService;

  public ProducedAnimalsQueueManager(BasicProducer animalProducer, RabbitMQChecker rabbitMQChecker, AnimalRegistrationService animalRegistrationService){
    this.rabbitMQChecker = rabbitMQChecker;
    this.animalProducer = animalProducer;
    this.animalRegistrationService = animalRegistrationService;
  }


  @Override public void run() {
    // Handle the unregistered Animals on its own thread:
    Thread handleUnregisteredAnimalsThread = new Thread(this::handleUnregisteredAnimals);
    handleUnregisteredAnimalsThread.setDaemon(true);
    handleUnregisteredAnimalsThread.start();

    // Handle the registered Animals on its own thread:
    Thread handleregisteredAnimalsThread = new Thread(this::handleRegisteredAnimals);
    handleregisteredAnimalsThread.setDaemon(true);
    handleregisteredAnimalsThread.start();

    // Wait for these two threads to close:
    try {
      handleUnregisteredAnimalsThread.join();
      handleregisteredAnimalsThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleUnregisteredAnimals(){
    // Run continuously for as long as this thread lives:
    while(true){
      // Check if Queue is non-empty:
      if(!unregisteredAnimalQueue.isEmpty()){
        // Extract data:
        AnimalDto animal = unregisteredAnimalQueue.poll();
        try {
          BigDecimal weight = animal.getWeight_kilogram();
          String origin = animal.getOrigin();
          Date arrivalDate = animal.getArrivalDate();

          // Attempt to register next Animal in the Queue, using gRPC connection:
          AnimalDto registeredAnimal = animalRegistrationService.registerNewAnimal(weight, origin, arrivalDate);
          System.out.println("\n[QueueManager] Saved animal {" + registeredAnimal + "} to database.");

          // Add the registered entity to the proper Queue for indirect transmission to the next station:
          if(!this.addToRegisteredQueue(registeredAnimal)){
            // Registered entity was not properly added to the queue. Throw an exception.
            throw new RuntimeException("Critical Failure: Failed to add the registered Animal to the queue, before transmission via RabbitMq.");
          }
        } catch (Exception e) {
          System.err.println("\n[QueueManager] Failed to register animal {" + animal + "}. Reason: " + e.getMessage());
          System.err.println("[QueueManager] Adding failed animal to the end of the queue, and trying next Animal in queue.");
          unregisteredAnimalQueue.offer(animal);
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

  private void handleRegisteredAnimals(){
    // Run continuously for as long as this thread lives:
    while(true){
      try {
        // Check if connection can be established with a RabbitMQ server:
        if(!rabbitMQChecker.isRabbitMQRunning()){
          System.err.println("\n\n[ProducedAnimalsQueueManager] Critical Error: Could not connect to RabbitMQ");
          System.err.println("[ProducedAnimalsQueueManager] Retrying in 5s...");
          Thread.sleep(5000);

        } else {
          // Try to retrieve an Entity from the Queue:
          try {
            AnimalDto animal = removeFirst();

            // Try to send this Entity, to the RabbitMq server:
            boolean success = animalProducer.sendMessage(animal);

            // If the RabbitMQ server did receive the message, re add the AnimalDto to the queue.
            if(!success){
              addToRegisteredQueue(animal);
            }

          } catch (NullPointerException e) {
            // Queue is empty. Sleep a bit, and check again:
            Thread.sleep(250);
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override public boolean addToUnregisteredQueue(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalDto");

    return unregisteredAnimalQueue.offer((AnimalDto) obj);
  }

  private boolean addToRegisteredQueue(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalDto");

    return registeredAnimalQueue.offer((AnimalDto) obj);
  }

  @Override public List<Object> copyRegisteredQueue() {
    return new LinkedList<>(registeredAnimalQueue);
  }

  private AnimalDto removeFirst() {
    AnimalDto animal = registeredAnimalQueue.poll();
    if(animal == null)
      throw new NullPointerException("Queue is empty");
    else
      return animal;
  }

}
