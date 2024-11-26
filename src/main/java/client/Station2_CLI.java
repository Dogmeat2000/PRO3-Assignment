package client;

import client.interfaces.AnimalPartRegistrationSystem;
import client.interfaces.AnimalRegistrationSystem;
import client.interfaces.PartTypeRegistrationSystem;
import client.interfaces.TrayRegistrationSystem;
import client.ui.Model.service.AnimalPartRegistrationSystemImpl;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import client.ui.Model.service.PartTypeRegistrationSystemImpl;
import client.ui.Model.service.TrayRegistrationSystemImpl;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.PartTypeDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Station2_CLI
{
  private static final AnimalRegistrationSystem animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);
  private static final AnimalPartRegistrationSystem animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090);
  private static final PartTypeRegistrationSystem partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl("localhost", 9090);
  private static final TrayRegistrationSystem trayRegistrationSystem = new TrayRegistrationSystemImpl("localhost", 9090);

  public static void main(String[] args) {
    System.out.println("\nSTATION 2: Animal Dissection (Command Line Interface)\nThis CLI is for debugging purposes!");

    while(true) {
      String input = null;

      while (!validateCommand(input)) {
        System.out.println("\nAvailable 'AnimalPart' commands: 'Add', 'Remove', 'Update', 'ViewOne' & 'ViewAll'");
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
    List<Long> validPartIds = new ArrayList<>();
    List<Long> validAnimalIds = new ArrayList<>();
    List<Long> validTrayIds = new ArrayList<>();
    List<Long> validTypeIds = new ArrayList<>();

    switch (input.toLowerCase()) {
      case "add":

        // Prompt user to enter weight of AnimalPart:
        System.out.print("Enter weight (kg) of animalPart to ADD: ");
        value = getUserInput();
        if(!validateBigDecimalInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        BigDecimal weight = new BigDecimal(value);

        //Show a list of valid Animals:
        System.out.println("\nValid Animals are:");
        for (AnimalDto animal : animalRegistrationSystem.getAllAnimals()) {
          System.out.println(animal);
          validAnimalIds.add(animal.getAnimalId());
        }

        // Prompt user to enter the id of Animal this part was cut from:
        System.out.print("Enter id of Animal this part came from: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        AnimalDto parentAnimal = null;
        try {
          parentAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid Animal Id!");
          break;
        }

        //Show a list of valid PartTypes:
        System.out.println("\nValid PartTypes are:");
        for (PartTypeDto partType : partTypeRegistrationSystem.getAllPartTypes()) {
          System.out.println(partType);
          validTypeIds.add(partType.getTypeId());
        }

        // Prompt user to enter the id of Type of part this is:
        System.out.print("Enter id of PartType (foot, chest, etc) that this AnimalPart is: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartTypeDto parentPartType = null;
        try {
          parentPartType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid PartType Id!");
          break;
        }

        //Show a list of valid Trays:
        System.out.println("\nValid Trays are:");
        for (TrayDto tray : trayRegistrationSystem.getAllTrays()) {
          // Calculate available space:
          BigDecimal availableSpace = tray.getMaxWeight_kilogram().subtract(tray.getWeight_kilogram());

          // Only display Trays with enough space remaining, and of the proper type:
          if(availableSpace.subtract(weight).compareTo(BigDecimal.valueOf(0)) > 0
              && (tray.getTrayTypeId() == 0
              || tray.getTrayTypeId() == parentPartType.getTypeId())) {

            System.out.println(tray);
            validTrayIds.add(tray.getTrayId());
          }
        }

        // Prompt user to enter the id of the Tray this AnimalPart should be put into:
        System.out.print("Enter id of Tray that this AnimalPart should be transported in: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTrayIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        TrayDto parentTray = null;
        try {
          parentTray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid Tray Id!");
          break;
        }

        // Save the AnimalPart to the database:
        try {
          AnimalPartDto animalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);
          System.out.println("Added [" + animalPart + "] to Database!");
        } catch (CreateFailedException e) {
          System.out.println("AnimalPartRegistration failed, " + e.getMessage());
          break;
        }
        break;


      case "remove":
        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        for (AnimalPartDto animalPart : animalPartRegistrationSystem.getAllAnimalParts()) {
          System.out.println(animalPart);
          validPartIds.add(animalPart.getPartId());
        }

        // Prompt user to enter the part_id to remove:
        System.out.print("Enter part_id of AnimalPart to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validPartIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long part_idToRemove = Long.parseLong(value);

        // Remove the AnimalPart
        try {
          AnimalPartDto partToRemove = animalPartRegistrationSystem.readAnimalPart(part_idToRemove);
          if(animalPartRegistrationSystem.removeAnimalPart(partToRemove))
            System.out.println("Removed AnimalPart with Part_Id '" + part_idToRemove + "' from Database!");
        } catch (DeleteFailedException | NotFoundException e) {
          System.out.println("ERROR: Could not remove designated AnimalPart from Database");
          break;
        }
        break;


      case "update":
        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        for (AnimalPartDto animalPart : animalPartRegistrationSystem.getAllAnimalParts()) {
          System.out.println(animalPart);
          validPartIds.add(animalPart.getPartId());
          validAnimalIds.add(animalPart.getAnimalId());
          validTypeIds.add(animalPart.getTypeId());
        }

        // Prompt user to enter the part_id to update:
        System.out.print("Enter part_id belonging to AnimalPart to update: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validPartIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long part_idToUpdate = Long.parseLong(value);

        // Read the AnimalPart to modify from the database:
        AnimalPartDto modifiedAnimalPart = null;
        try {
          modifiedAnimalPart = animalPartRegistrationSystem.readAnimalPart(part_idToUpdate);
          if(modifiedAnimalPart != null)
            System.out.println("You are modifying AnimalPart\n: " + modifiedAnimalPart);
        } catch (NotFoundException e) {
          System.out.println("ERROR: Could not retrieve original AnimalPart from Database. It no longer exists in database.");
          break;
        }
        AnimalPartDto oldAnimalPart = modifiedAnimalPart.copy();

        // Prompt user for modified values:
        // Prompt user for new Weight:
        System.out.println("Please enter new weight_kilogram for this AnimalPart (Old is: " + oldAnimalPart.getWeight_kilogram() + "kg: ");

        value = getUserInput();
        if(!validateBigDecimalInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        BigDecimal newUpdatedWeight = new BigDecimal(value);

        // Prompt user for new Parent Animal:
        //Show a list of valid Animals:
        System.out.println("\nValid Animals are:");
        for (AnimalDto animal : animalRegistrationSystem.getAllAnimals()) {
          System.out.println(animal);
        }

        // Prompt user to enter the new id of Animal this part was cut from:
        System.out.println("Please enter new animal_id for this AnimalPart (Old is: " + oldAnimalPart.getAnimalId() + "): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        AnimalDto newParentAnimal = null;
        try {
          newParentAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid Animal Id!");
          break;
        }
        modifiedAnimalPart.setAnimalId(newParentAnimal.getAnimalId());

        // Prompt user for new Parent PartType:
        //Show a list of valid PartTypes:
        System.out.println("\nValid PartTypes are:");
        for (PartTypeDto partType : partTypeRegistrationSystem.getAllPartTypes()) {
          System.out.println(partType);
        }

        // Prompt user to enter the id of Type of part this AnimalPart should become:
        System.out.println("Please enter new Type_id for this AnimalPart (Old is: " + oldAnimalPart.getTypeId() + "): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartTypeDto newParentPartType = null;
        try {
          newParentPartType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid PartType Id!");
          break;
        }
        modifiedAnimalPart.setTypeId(newParentPartType.getTypeId());

        //Show a list of valid Trays:
        System.out.println("\nValid Trays are:");
        for (TrayDto tray : trayRegistrationSystem.getAllTrays()) {
          // Calculate available space:
          BigDecimal availableSpace = tray.getMaxWeight_kilogram().subtract(tray.getWeight_kilogram());

          // Only display Trays with space remaining, and of the proper type:
          if(availableSpace.subtract(modifiedAnimalPart.getWeight_kilogram()).compareTo(BigDecimal.valueOf(0)) > 0
              && (tray.getTrayTypeId() == 0
              || tray.getTrayTypeId() == modifiedAnimalPart.getTypeId())) {

            System.out.println("Tray_id: '" + tray.getTrayId() + "', Available space: '" + availableSpace + "kg'");
            validTrayIds.add(tray.getTrayId());
          }
        }
        validTrayIds.add(modifiedAnimalPart.getTrayId());

        // Prompt user to enter the tray_id associated with the AnimalPart to update:
        System.out.println("Please enter new Tray_id for this AnimalPart (Old is: " + oldAnimalPart.getTrayId() + "): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validTrayIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        TrayDto newParentTray = null;
        try {
          newParentTray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid Tray_Id!");
          break;
        }
        modifiedAnimalPart.setTrayId(newParentTray.getTrayId());

        // Attempt to persist the user defined modifications:
        modifiedAnimalPart.setWeight_kilogram(newUpdatedWeight);
        try {
          animalPartRegistrationSystem.updateAnimalPart(modifiedAnimalPart);
        } catch (UpdateFailedException e) {
          System.out.println("Update failed. Reason: " + e.getMessage());
        } catch (NotFoundException e) {
          System.out.println("Unable to update. Could not find AnimalPart in Repository");
        }
        break;


      case "viewone":
        // Prompt user to enter the part_id to view:
        System.out.print("Enter part_id to belonging to the AnimalPart you want to view: ");
        value = getUserInput();
        if(!validateLongInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        long partId = Long.parseLong(value);

       // Read the AnimalPart to from the database:
        AnimalPartDto animalPart;
        try {
          animalPart = animalPartRegistrationSystem.readAnimalPart(partId);
          System.out.println("Found [" + animalPart + "]");
        } catch (NotFoundException e) {
          System.out.println("ERROR: Could not retrieve original AnimalPart from Database. It no longer exists in database.");
          break;
        }
        break;


      case "viewall":
        //Show a list of valid AnimalParts:
        System.out.println("\nRetrieving all AnimalParts from Database: ");
        try {
          List<AnimalPartDto> animalPartsFound = animalPartRegistrationSystem.getAllAnimalParts();
          if(!animalPartsFound.isEmpty()){
            for (AnimalPartDto localAnimalPart : animalPartsFound) {
              System.out.println(localAnimalPart);
            }
          } else {
            throw new NotFoundException("");
          }

        } catch (NotFoundException e) {
          System.out.println("Could not find any AnimalParts in repository");
        }
        break;

      default:
        System.out.println("Invalid input!");
    }
  }
}
