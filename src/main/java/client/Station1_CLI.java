package client;

import client.interfaces.AnimalRegistrationSystem;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import server.model.persistence.entities.Animal;
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

public class Station1_CLI
{
  private static final AnimalRegistrationSystem animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);


  public static void main(String[] args) {
    System.out.println("\nSTATION 1: Animal Registration (Command Line Interface)\nThis CLI is for debugging purposes!");


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
        if(value.isEmpty() ||value.isBlank())
          System.out.println("Invalid input!");
        else
          try {
            Date today = Date.from(Instant.now()); // UTC Time
            System.out.println("NOTE Animal will be registered as having arrived at '" + today + "' UTC Time");
            AnimalDto animal = animalRegistrationSystem.registerNewAnimal(newAnimalWeight, value, today);
            System.out.println("Added [" + animal + "] to Database!");
          } catch (CreateFailedException e) {
            System.out.println("Invalid input!");
          }
        break;


      case "remove":
        System.out.print("Enter animal_id of animal to REMOVE: ");
        value = getUserInput();
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else
          try {
            if(animalRegistrationSystem.removeAnimal(Long.parseLong(value)))
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
            oldAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
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
        if(value.isEmpty() ||value.isBlank() || oldAnimal == null)
          System.out.println("Invalid input!");
        else
          try {
            Date oldRegistryDate = oldAnimal.getArrivalDate(); // UTC Time
            System.out.println("NOTE original Animal registration date remains as '" + oldRegistryDate + "' UTC Time");
            AnimalDto newAnimal = new AnimalDto(animalId, newWeight, value, oldRegistryDate, oldAnimal.getAnimalPartIdList());
            animalRegistrationSystem.updateAnimal(newAnimal);
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
            AnimalDto animal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + animal + "]");
          } catch (NotFoundException e) {
            System.out.println("No Animals found in Database!");
          }
        break;


      case "viewall":
        System.out.println("Retrieving all Animals from Database: ");
        try {
          List<AnimalDto> animals = animalRegistrationSystem.getAllAnimals();

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
