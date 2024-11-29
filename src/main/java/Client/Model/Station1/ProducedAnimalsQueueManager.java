package Client.model.Station1;

import Client.model.QueueManager;
import Client.network.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducedAnimalsQueueManager implements QueueManager
{
  Queue<AnimalDto> registeredAnimalQueue = new LinkedList<>();
  BasicProducer animalProducer;
  RabbitMQChecker rabbitMQChecker;

  public ProducedAnimalsQueueManager(BasicProducer animalProducer, RabbitMQChecker rabbitMQChecker){
    this.animalProducer = animalProducer;
    this.rabbitMQChecker = rabbitMQChecker;
  }

  @Override public void run() {
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
              addLast(animal);
            }

          } catch (NullPointerException e) {
            // Queue is empty. Sleep a bit, and check again:
            Thread.sleep(250);
          }
        }

      } catch (Exception e) {
        e.printStackTrace(); // TODO: Implement better exception handling.
      }
    }

  }


  @Override public boolean addLast(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalDto");

    return registeredAnimalQueue.offer((AnimalDto) obj);
  }

  @Override public List<Object> copyQueue() {
    return new LinkedList<>(registeredAnimalQueue);
  }

  @Override public boolean findAndConsume(Object obj) {
    // Validate:
    if(!(obj instanceof AnimalDto))
      return false;

    AnimalDto animal = (AnimalDto) obj;

    // Create a backup of the original, so it can be restored:
    List<Object> queueToMaintain = new LinkedList<>();
    List<Object> copyOfQueue = copyQueue();
    boolean foundMatch = false;

    // Iterate through the copy (so we don't modify the true list):
    for (Object o : copyOfQueue) {
      if(o instanceof AnimalDto && ((AnimalDto) o).getAnimalId() != animal.getAnimalId()){
        queueToMaintain.add(o);
      } else if (o instanceof AnimalDto && ((AnimalDto) o).getAnimalId() == animal.getAnimalId()){
        foundMatch = true;
      }
    }

    // If a match was found, we iterate through the Queue until we find the match, and don't re-add it. Everything else is re-added.
    if(foundMatch){
      while(!registeredAnimalQueue.isEmpty()){
        AnimalDto dto = registeredAnimalQueue.poll();
        if(dto.getAnimalId() == animal.getAnimalId()){
          return true;
        } else {
          registeredAnimalQueue.offer(dto);
        }
      }
    }
    return false;
  }

  private AnimalDto removeFirst() {
    AnimalDto animal = registeredAnimalQueue.poll();
    if(animal == null)
      throw new NullPointerException("Queue is empty");
    else
      return animal;
  }

}
