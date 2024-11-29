package Client.model.Station2;

import Client.model.QueueManager;
import Client.network.services.rabbitAmqp.BasicProducer;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalPartDto;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducedAnimalPartsQueueManager implements QueueManager
{
  Queue<AnimalPartDto> registeredAnimalPartQueue = new LinkedList<>();
  BasicProducer animalPartProducer;
  RabbitMQChecker rabbitMQChecker;

  public ProducedAnimalPartsQueueManager(BasicProducer animalPartProducer, RabbitMQChecker rabbitMQChecker){
    this.animalPartProducer = animalPartProducer;
    this.rabbitMQChecker = rabbitMQChecker;
  }

  @Override public void run() {
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
              addLast(animalPart);
            }

          } catch (NullPointerException e) {
            // Queue is empty. Sleep a bit, and check again:
            Thread.sleep(1000);
          }
        }

      } catch (Exception e) {
        e.printStackTrace(); // TODO: Implement better exception handling.
      }
    }

  }


  @Override public boolean addLast(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof AnimalPartDto))
      throw new IllegalArgumentException("obj is not an instance of AnimalDto");

    return registeredAnimalPartQueue.offer((AnimalPartDto) obj);
  }

  @Override public List<Object> copyQueue() {
    return new LinkedList<>(registeredAnimalPartQueue);
  }

  @Override public boolean findAndConsume(Object obj) {
    // Validate:
    if(!(obj instanceof AnimalPartDto))
      return false;

    AnimalPartDto animalPart = (AnimalPartDto) obj;

    // Create a backup of the original, so it can be restored:
    List<Object> queueToMaintain = new LinkedList<>();
    List<Object> copyOfQueue = copyQueue();
    boolean foundMatch = false;

    // Iterate through the copy (so we don't modify the true list):
    for (Object o : copyOfQueue) {
      if(o instanceof AnimalPartDto && ((AnimalPartDto) o).getPartId() != animalPart.getPartId()){
        queueToMaintain.add(o);
      } else if (o instanceof AnimalPartDto && ((AnimalPartDto) o).getPartId() == animalPart.getPartId()){
        foundMatch = true;
      }
    }

    // If a match was found, we iterate through the Queue until we find the match, and don't re-add it. Everything else is re-added.
    if(foundMatch){
      while(!registeredAnimalPartQueue.isEmpty()){
        AnimalPartDto dto = registeredAnimalPartQueue.poll();
        if(dto.getPartId() == animalPart.getPartId()){
          return true;
        } else {
          registeredAnimalPartQueue.offer(dto);
        }
      }
    }
    return false;
  }

  private AnimalPartDto removeFirst() {
    AnimalPartDto animalPart = registeredAnimalPartQueue.poll();
    if(animalPart == null)
      throw new NullPointerException("Queue is empty");
    else
      return animalPart;
  }
}
