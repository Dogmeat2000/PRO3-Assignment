package client;

import client.interfaces.AnimalRegistrationSystem;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import shared.model.entities.Animal;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        if(!validateBigDecimalInput(value))
          System.out.println("Invalid input!");
        else
          try {
            Animal animal = animalRegistrationSystem.registerNewAnimal(new BigDecimal(value));
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
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else {
          try {
            Animal animal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + animal + "]");
          }
          catch (NotFoundException e) {
            System.out.println("Invalid input!");
            break;
          }
        }
        long animalId = Long.parseLong(value);
        System.out.print("Enter new weight: ");
        value = getUserInput();
        if(!validateBigDecimalInput(value))
          System.out.println("Invalid input!");
        else {
          try {
            Animal animal = new Animal(animalId, new BigDecimal(value));
            animalRegistrationSystem.updateAnimal(animal);
            System.out.println("Updated [" + animal + "]");
          }
          catch (UpdateFailedException | NotFoundException e) {
            e.printStackTrace();
            System.out.println("Invalid input!");
            break;
          }
        }
        break;

      case "viewone":
        System.out.print("Enter animal_id: ");
        value = getUserInput();
        if(!validateLongInput(value))
          System.out.println("Invalid input!");
        else
          try {
            Animal animal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + animal + "]");
          } catch (NotFoundException e) {
            System.out.println("No Animals found in Database!");
          }
        break;

      case "viewall":
        System.out.println("Retrieving all Animals from Database: ");
        try {
          List<Animal> animals = animalRegistrationSystem.getAllAnimals();

          if(animals.isEmpty())
            throw new NotFoundException("");

          for (Animal a : animals)
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
