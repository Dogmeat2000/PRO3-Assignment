package Client.Station1_AnimalRegistration;

import Client.Station1_AnimalRegistration.model.ProducedAnimalsQueueManager;
import Client.Station1_AnimalRegistration.model.Station1Model;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationServiceImpl;
import Client.common.services.rabbitAmqp.BasicProducer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shared.controller.rabbitMQ.RabbitMQChecker;
import shared.model.dto.AnimalDto;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class RunStation1CLI
{
  private static final AnimalRegistrationService ANIMAL_REGISTRATION_SERVICE = new AnimalRegistrationServiceImpl("localhost", 9090);
  private static final BasicProducer ANIMAL_PRODUCER = new BasicProducer("QAni", "SlaughterHouse","Animal", "localhost", 5672);
  private static final RabbitMQChecker RABBIT_MQ_CHECKER = new RabbitMQChecker("localhost", 5672);
  private static final ProducedAnimalsQueueManager QUEUE_MANAGER = new ProducedAnimalsQueueManager(ANIMAL_PRODUCER, RABBIT_MQ_CHECKER);
  private static final Station1Model STATION_1_MODEL = new Station1Model(ANIMAL_REGISTRATION_SERVICE, QUEUE_MANAGER);



  public static void main(String[] args) {
    System.out.println("\nSTATION 1: Animal Registration (Command Line Interface)\nThis CLI is for debugging purposes!");

    // Boot up the QueueManager:
    Thread queueManagerThread = new Thread(QUEUE_MANAGER);
    queueManagerThread.setDaemon(true);
    queueManagerThread.start();

    while(true) {
      String input = null;

      while (!validateCommand(input)) {
        System.out.println("\nAvailable 'Animal' commands: 'Add', 'Remove', 'Update', 'ViewOne' & 'ViewAll'");
        System.out.print(": ");
        input = getUserInput();
        if(validateCommand(input))
          break;
        else
          System.out.println("\nPlease enter a valid command");
      }

      performCommand(input);
    }
  }


  private static String getUserInput() {
    Scanner input = new Scanner(System.in);
    return input.nextLine();
  }


  private static boolean validateCommand(String input) {
    return input != null && !input.isBlank() & !input.isEmpty() && inputContainsLegalCommand(input);
  }


  private static boolean inputContainsLegalCommand(String input) {
    List<String> legalCommands = new ArrayList<>();
    legalCommands.add("Add");
    legalCommands.add("Remove");
    legalCommands.add("Update");
    legalCommands.add("ViewOne");
    legalCommands.add("ViewAll");

    for (String command : legalCommands) {
      if(input.toLowerCase().contains(command.toLowerCase()) && input.length() == command.length())
        return true;
    }

    return false;
  }


  private static boolean validateBigDecimalInput(String input) {
    try {
      new BigDecimal(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  private static boolean validateLongInput(String input) {
    try {
      Long.parseLong(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  private static void performCommand(String input) {
    String value;

    switch (input.toLowerCase()) {
      case "add":
        System.out.print("Enter animal weight (kg) of animal to ADD: ");
        value = getUserInput();
        if(!validateBigDecimalInput(value)) {
          System.out.println("Invalid input!");
          break;
        }
        BigDecimal newAnimalWeight = new BigDecimal(value);

        System.out.print("Enter name of origin farm: ");
        value = getUserInput();
        if(value.isBlank())
          System.out.println("Invalid input!");
        else
          try {
            Date today = Date.from(Instant.now()); // UTC Time
            System.out.println("NOTE Animal will be registered as having arrived at '" + today + "' UTC Time");
            AnimalDto animal = STATION_1_MODEL.registerNewAnimal(newAnimalWeight, value, today);
            System.out.println("Added [" + animal + "] to Database!");
            Thread.sleep(750); // Give the RabbitMQ server some time to write its confirmation message in the terminal.
          } catch (CreateFailedException e) {
            e.printStackTrace();
            System.out.println("Invalid input!");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        break;


      case "remove":
        System.out.print("Enter animal_id of animal to REMOVE: ");
        value = getUserInput();
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else
          try {
            if(STATION_1_MODEL.removeAnimal(Long.parseLong(value)))
              System.out.println("Removed Animal with ID '" + value + "' from Database!");
          } catch (DeleteFailedException | NotFoundException e) {
            System.out.println("Invalid input!");
          }
        break;


      case "update":
        System.out.print("Enter animal_id: ");
        value = getUserInput();
        AnimalDto oldAnimal = null;
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else {
          try {
            oldAnimal = STATION_1_MODEL.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + oldAnimal + "]");
          }
          catch (NotFoundException e) {
            System.out.println("Invalid input!");
            break;
          }
        }
        long animalId = Long.parseLong(value);

        System.out.print("Enter new weight: ");
        value = getUserInput();
        if(!validateBigDecimalInput(value)) {
          System.out.println("Invalid input!");
          break;
        }
        BigDecimal newWeight = new BigDecimal(value);

        System.out.print("Enter new name of origin farm: ");
        value = getUserInput();
        if(value.isBlank() || oldAnimal == null)
          System.out.println("Invalid input!");
        else
          try {
            Date oldRegistryDate = oldAnimal.getArrivalDate(); // UTC Time
            System.out.println("NOTE original Animal registration date remains as '" + oldRegistryDate + "' UTC Time");
            AnimalDto newAnimal = new AnimalDto(animalId, newWeight, value, oldRegistryDate, oldAnimal.getAnimalPartIdList());
            STATION_1_MODEL.updateAnimal(newAnimal);
            System.out.println("Implemented updated Animal [" + newAnimal + "] in Database!");
          } catch (UpdateFailedException | NotFoundException e) {
            e.printStackTrace(); // TODO: DELETE LINE
            System.out.println("Invalid input!");
            break;
          }
        break;


      case "viewone":
        System.out.print("Enter animal_id: ");
        value = getUserInput();
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else
          try {
            AnimalDto animal = STATION_1_MODEL.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + animal + "]");
          } catch (NotFoundException e) {
            System.out.println("No Animals found in Database!");
          }
        break;


      case "viewall":
        System.out.println("Retrieving all Animals from Database: ");
        try {
          List<AnimalDto> animals = STATION_1_MODEL.getAllAnimals();

          if(animals.isEmpty())
            throw new NotFoundException("");

          for (AnimalDto a : animals)
            System.out.println("[" + a + "]");

        } catch (NotFoundException e) {
          System.out.println("No Animals found in Database!");
        }
        break;

      default:
        System.out.println("Invalid input!");
    }
  }
}
