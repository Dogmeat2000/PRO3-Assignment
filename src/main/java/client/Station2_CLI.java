package client;

import client.interfaces.AnimalPartRegistrationSystem;
import client.interfaces.AnimalRegistrationSystem;
import client.interfaces.PartTypeRegistrationSystem;
import client.interfaces.TrayRegistrationSystem;
import client.ui.Model.service.AnimalPartRegistrationSystemImpl;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import client.ui.Model.service.PartTypeRegistrationSystemImpl;
import client.ui.Model.service.TrayRegistrationSystemImpl;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.entities.Tray;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

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
        validPartIds.clear();
        validAnimalIds.clear();
        validTrayIds.clear();
        validTypeIds.clear();

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
        for (Animal animal : animalRegistrationSystem.getAllAnimals()) {
          System.out.println("id: '" + animal.getId() + "', Weight_kilogram: '" + animal.getWeight_kilogram() + "', Parts: " + animal.getPartList());
          validAnimalIds.add(animal.getId());
        }

        // Prompt user to enter the id of Animal this part was cut from:
        System.out.print("Enter id of Animal this part came from: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Animal parentAnimal = null;
        try {
          parentAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Animal Id!");
          break;
        }

        //Show a list of valid PartTypes:
        System.out.println("\nValid PartTypes are:");
        for (PartType partType : partTypeRegistrationSystem.getAllPartTypes()) {
          System.out.println("id: '" + partType.getTypeId() + "', Description: '" + partType.getTypeDesc() + "'");
          validTypeIds.add(partType.getTypeId());
        }

        // Prompt user to enter the id of Type of part this is:
        System.out.print("Enter id of PartType (foot, chest, etc) that this AnimalPart is: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartType parentPartType = null;
        try {
          parentPartType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid PartType Id!");
          break;
        }

        //Show a list of valid Trays:
        System.out.println("\nValid Trays are:");
        for (Tray tray : trayRegistrationSystem.getAllTrays()) {
          // Calculate available space:
          BigDecimal availableSpace = tray.getMaxWeight_kilogram().subtract(tray.getWeight_kilogram());
          // Only display Trays with space remaining:
          if(availableSpace.subtract(weight).compareTo(BigDecimal.valueOf(0)) > 0) {
            System.out.println("Tray_id: '" + tray.getTrayId() + "', Available space: '" + availableSpace + "kg'");
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
        Tray parentTray = null;
        try {
          parentTray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Tray Id!");
          break;
        }

        // Save the AnimalPart to the database:
        try {
          AnimalPart animalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);
          // Update associated Tray repository:
          parentTray.addAnimalPart(animalPart);
          trayRegistrationSystem.updateTray(parentTray);

          // Update associated PartType repository:
          parentPartType.getPartList().add(animalPart);
          partTypeRegistrationSystem.updatePartType(parentPartType);

          // Update associated Animal repository:
          parentAnimal.getPartList().add(animalPart);
          animalRegistrationSystem.updateAnimal(parentAnimal);

          System.out.println("Added [" + animalPart + "] to Database!");
        } catch (CreateFailedException e) {
          e.printStackTrace();
          System.out.println("AnimalPartRegistration failed, " + e.getMessage());
          break;
        }
        break;


      case "remove":
        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        validPartIds.clear();
        validAnimalIds.clear();
        validTrayIds.clear();
        validTypeIds.clear();
        for (AnimalPart animalPart : animalPartRegistrationSystem.getAllAnimalParts()) {
          System.out.println("Part_id: '"
              + animalPart.getPart_id()
              + "', Type_id: '"
              + animalPart.getType().getTypeId()
              + "' ("
              + animalPart.getType().getTypeDesc()
              + "' "
              + "Cut from animal_id: '" + animalPart.getAnimal().getId()
              + "', Transported in tray_id: '"
              + animalPart.getTray().getTrayId()
              + "', with weight: '"
              + animalPart.getWeight_kilogram()
              + "'");
          validPartIds.add(animalPart.getPart_id());
          validAnimalIds.add(animalPart.getAnimal().getId());
          validTypeIds.add(animalPart.getType().getTypeId());
          validTrayIds.add(animalPart.getTray().getTrayId());
        }

        // Prompt user to enter the part_id to remove:
        System.out.print("Enter part_id to remove belonging to the AnimalPart you want to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validPartIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long part_idToRemove = Long.parseLong(value);

        // Prompt user to enter the animal_id associated with the AnimalPart to remove:
        System.out.print("Enter animal_id belonging to the AnimalPart you want to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Animal parentAnimalToRemoveFrom = null;
        try {
          parentAnimalToRemoveFrom = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Animal_Id!");
          break;
        }

        // Prompt user to enter the type_id associated with the AnimalPart to remove:
        System.out.print("Enter type_id belonging to the AnimalPart you want to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartType parentPartTypeToRemoveFrom = null;
        try {
          parentPartTypeToRemoveFrom = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Type_Id!");
          break;
        }

        // Prompt user to enter the tray_id associated with the AnimalPart to remove:
        System.out.print("Enter tray_id belonging to the AnimalPart you want to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTrayIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Tray parentTrayToRemoveFrom = null;
        try {
          parentTrayToRemoveFrom = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Tray_Id!");
          break;
        }

        // Remove the AnimalPart
        try {
          AnimalPart partToRemove = animalPartRegistrationSystem.readAnimalPart(part_idToRemove, parentAnimalToRemoveFrom, parentPartTypeToRemoveFrom, parentTrayToRemoveFrom);
          if(animalPartRegistrationSystem.removeAnimalPart(partToRemove))
            System.out.println("Removed AnimalPart with Part_Id '" + part_idToRemove + "' from Database!");
        } catch (DeleteFailedException | NotFoundException e) {
          e.printStackTrace();
          System.out.println("ERROR: Could not remove designated AnimalPart from Database");
          break;
        }
        break;


      case "update":
        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        validPartIds.clear();
        validAnimalIds.clear();
        validTrayIds.clear();
        validTypeIds.clear();
        for (AnimalPart animalPart : animalPartRegistrationSystem.getAllAnimalParts()) {
          System.out.println("Part_id: '"
              + animalPart.getPart_id()
              + "', Type_id: '"
              + animalPart.getType().getTypeId()
              + "' ("
              + animalPart.getType().getTypeDesc()
              + "' "
              + "Cut from animal_id: '" + animalPart.getAnimal().getId()
              + "', Transported in tray_id: '"
              + animalPart.getTray().getTrayId()
              + "', with weight: '"
              + animalPart.getWeight_kilogram()
              + "'");
          validPartIds.add(animalPart.getPart_id());
          validAnimalIds.add(animalPart.getAnimal().getId());
          validTypeIds.add(animalPart.getType().getTypeId());
          validTrayIds.add(animalPart.getTray().getTrayId());
        }

        // Prompt user to enter the part_id to update:
        System.out.print("Enter part_id to remove belonging to the AnimalPart you want to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validPartIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long part_idToUpdate = Long.parseLong(value);

        // Prompt user to enter the animal_id associated with the AnimalPart to update:
        System.out.print("Enter animal_id belonging to the AnimalPart you want to update: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Animal oldParentAnimal = null;
        try {
          oldParentAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Animal_Id!");
          break;
        }

        // Prompt user to enter the type_id associated with the AnimalPart to update:
        System.out.print("Enter type_id belonging to the AnimalPart you want to update: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartType oldParentPartType = null;
        try {
          oldParentPartType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Type_Id!");
          break;
        }

        // Prompt user to enter the tray_id associated with the AnimalPart to update:
        System.out.print("Enter tray_id belonging to the AnimalPart you want to update: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validTrayIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Tray oldParentTray = null;
        try {
          oldParentTray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Tray_Id!");
          break;
        }

        // Read the AnimalPart to modify from the database:
        AnimalPart modifiedAnimalPart = null;
        try {
          modifiedAnimalPart = animalPartRegistrationSystem.readAnimalPart(part_idToUpdate, oldParentAnimal, oldParentPartType, oldParentTray);
          if(modifiedAnimalPart != null)
            System.out.println("You are modifying AnimalPart\n: " + modifiedAnimalPart);
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("ERROR: Could not retrieve original AnimalPart from Database. It no longer exists in database.");
          break;
        }
        AnimalPart oldAnimalPart = modifiedAnimalPart.copy();

        // Prompt user for modified values:
        // Prompt user for new Weight:
        if(oldAnimalPart != null)
          System.out.println("Please enter new weight_kilogram for this AnimalPart (Old is: " + oldAnimalPart.getWeight_kilogram() + "kg: ");
        else
          System.out.println("Please enter new weight_kilogram for this AnimalPart (Old is: NULL kg): ");

        value = getUserInput();
        if(!validateBigDecimalInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        BigDecimal newUpdatedWeight = new BigDecimal(value);

        // Prompt user for new Parent Animal:
        //Show a list of valid Animals:
        System.out.println("\nValid Animals are:");
        for (Animal animal : animalRegistrationSystem.getAllAnimals()) {
          System.out.println("id: '" + animal.getId() + "', Weight_kilogram: '" + animal.getWeight_kilogram() + "', Parts: " + animal.getPartList());
        }

        // Prompt user to enter the new id of Animal this part was cut from:
        if(oldAnimalPart != null)
          System.out.println("Please enter new animal_id for this AnimalPart (Old is: " + oldAnimalPart.getAnimal().getId() + "): ");
        else
          System.out.println("Please enter new animal_id for this AnimalPart (Old is: NULL): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Animal newParentAnimal = null;
        try {
          newParentAnimal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Animal Id!");
          break;
        }

        // Prompt user for new Parent PartType:
        //Show a list of valid PartTypes:
        System.out.println("\nValid PartTypes are:");
        for (PartType partType : partTypeRegistrationSystem.getAllPartTypes()) {
          System.out.println("id: '" + partType.getTypeId() + "', Description: '" + partType.getTypeDesc() + "'");
        }

        // Prompt user to enter the id of Type of part this AnimalPart should become:
        if(modifiedAnimalPart != null)
          System.out.println("Please enter new Type_id for this AnimalPart (Old is: " + oldAnimalPart.getType().getTypeId() + "): ");
        else
          System.out.println("Please enter new Type_id for this AnimalPart (Old is: NULL): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validTypeIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        PartType newParentPartType = null;
        try {
          newParentPartType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid PartType Id!");
          break;
        }

        //Show a list of valid Trays:
        System.out.println("\nValid Trays are:");
        for (Tray tray : trayRegistrationSystem.getAllTrays()) {
          // Calculate available space:
          BigDecimal availableSpace = tray.getMaxWeight_kilogram().subtract(tray.getWeight_kilogram());
          // Only display Trays with space remaining:
          if(modifiedAnimalPart != null) {
            if(availableSpace.subtract(modifiedAnimalPart.getWeight_kilogram()).compareTo(BigDecimal.valueOf(0)) > 0) {
              System.out.println("Tray_id: '" + tray.getTrayId() + "', Available space: '" + availableSpace + "kg'");
              validTrayIds.add(tray.getTrayId());
            }
          } else {
            if(availableSpace.compareTo(BigDecimal.valueOf(0)) > 0) {
              System.out.println("Tray_id: '" + tray.getTrayId() + "', Available space: '" + availableSpace + "kg'");
              validTrayIds.add(tray.getTrayId());
            }
          }
        }
        if(modifiedAnimalPart != null)
          validTrayIds.add(modifiedAnimalPart.getTray().getTrayId());

        // Prompt user to enter the tray_id associated with the AnimalPart to update:
        if(modifiedAnimalPart != null)
          System.out.println("Please enter new Tray_id for this AnimalPart (Old is: " + oldAnimalPart.getTray().getTrayId() + "): ");
        else
          System.out.println("Please enter new Tray_id for this AnimalPart (Old is: NULL): ");

        value = getUserInput();
        if(!validateLongInput(value) || !validTrayIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Tray newParentTray = null;
        try {
          newParentTray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Tray_Id!");
          break;
        }

        // Attempt to persist the user defined modifications:
        modifiedAnimalPart.setWeight_kilogram(newUpdatedWeight);
        try {
          animalPartRegistrationSystem.updateAnimalPart(oldAnimalPart, modifiedAnimalPart);
        } catch (UpdateFailedException e) {
          System.out.println("Update failed. Reason: " + e.getMessage());
        } catch (NotFoundException e) {
          System.out.println("Unable to update. Could not find AnimalPart in Repository");
        }

        // Attempt to Update all the old associations.
        oldParentAnimal.getPartList().remove(modifiedAnimalPart);
        oldParentPartType.getPartList().remove(modifiedAnimalPart);
        oldParentTray.getContents().remove(modifiedAnimalPart);
        animalRegistrationSystem.updateAnimal(oldParentAnimal);
        partTypeRegistrationSystem.updatePartType(oldParentPartType);
        trayRegistrationSystem.updateTray(oldParentTray);

        // Attempt to update all the new associations.
        if(modifiedAnimalPart != null && !newParentAnimal.getPartList().contains(modifiedAnimalPart))
          newParentAnimal.getPartList().add(modifiedAnimalPart);

        if(modifiedAnimalPart != null && !newParentTray.getContents().contains(modifiedAnimalPart))
          newParentTray.getContents().add(modifiedAnimalPart);

        if(modifiedAnimalPart != null && !newParentPartType.getPartList().contains(modifiedAnimalPart))
          newParentPartType.getPartList().add(modifiedAnimalPart);
        animalRegistrationSystem.updateAnimal(newParentAnimal);
        partTypeRegistrationSystem.updatePartType(newParentPartType);
        trayRegistrationSystem.updateTray(newParentTray);
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

        // Prompt user to enter the animal_id associated with the AnimalPart to view:
        System.out.print("Enter animal_id belonging to the AnimalPart you want to view: ");
        value = getUserInput();
        if(!validateLongInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        Animal animal = null;
        try {
          animal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Animal_Id!");
          break;
        }

        // Prompt user to enter the type_id associated with the AnimalPart to update:
        System.out.print("Enter type_id belonging to the AnimalPart you want to view: ");
        value = getUserInput();
        if(!validateLongInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        PartType partType = null;
        try {
          partType = partTypeRegistrationSystem.readPartType(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Type_Id!");
          break;
        }

        // Prompt user to enter the tray_id associated with the AnimalPart to view:
        System.out.print("Enter tray_id belonging to the AnimalPart you want to view: ");
        value = getUserInput();
        if(!validateLongInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        Tray tray = null;
        try {
          tray = trayRegistrationSystem.readTray(Long.parseLong(value));
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("Invalid Tray_Id!");
          break;
        }

        // Read the AnimalPart to modify from the database:
        AnimalPart animalPart = null;
        try {
          animalPart = animalPartRegistrationSystem.readAnimalPart(partId, animal, partType, tray);
          System.out.println("Found [" + animalPart + "]");
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("ERROR: Could not retrieve original AnimalPart from Database. It no longer exists in database.");
          break;
        }
        break;


      case "viewall":
        //Show a list of valid AnimalParts:
        System.out.println("\nRetrieving all AnimalParts from Database: ");
        try {
          List<AnimalPart> animalPartsFound = animalPartRegistrationSystem.getAllAnimalParts();
          if(!animalPartsFound.isEmpty()){
            for (AnimalPart localAnimalPart : animalPartRegistrationSystem.getAllAnimalParts()) {
              System.out.println("Part_id: '"
                  + localAnimalPart.getPart_id()
                  + "', Type_id: '"
                  + localAnimalPart.getType().getTypeId()
                  + "' ("
                  + localAnimalPart.getType().getTypeDesc()
                  + "' "
                  + "Cut from animal_id: '" + localAnimalPart.getAnimal().getId()
                  + "', Transported in tray_id: '"
                  + localAnimalPart.getTray().getTrayId()
                  + "', with weight: '"
                  + localAnimalPart.getWeight_kilogram()
                  + "'");
              validPartIds.add(localAnimalPart.getPart_id());
              validAnimalIds.add(localAnimalPart.getAnimal().getId());
              validTypeIds.add(localAnimalPart.getType().getTypeId());
              validTrayIds.add(localAnimalPart.getTray().getTrayId());
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
