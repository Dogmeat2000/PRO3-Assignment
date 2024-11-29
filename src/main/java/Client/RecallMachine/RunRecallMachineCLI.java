package Client.RecallMachine;

import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationServiceImpl;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationServiceImpl;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystem;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystemImpl;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.ProductDto;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RunRecallMachineCLI
{
  private static final AnimalPartRegistrationService ANIMAL_PART_REGISTRATION_SERVICE = new AnimalPartRegistrationServiceImpl("localhost", 9090);
  private static final ProductRegistrationSystem productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
  private static final AnimalRegistrationService ANIMAL_REGISTRATION_SERVICE = new AnimalRegistrationServiceImpl("localhost", 9090);


  public static void main(String[] args) {
    System.out.println("\nSTATION 4: Information Catalogue (Command Line Interface)\nThis CLI is for debugging purposes!");

    while(true) {
      String input = null;

      while (!validateCommand(input)) {
        System.out.println("\nAvailable commands:"
            + "\ntype '1' to: Retrieve registration number for all animals involved in a given Product."
            + "\ntype '2' to: Retrieve all Products a given Animal is involved in.");
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
    legalCommands.add("1");
    legalCommands.add("2");

    for (String command : legalCommands)
      if(input.toLowerCase().contains(command.toLowerCase()) && input.length() == command.length())
        return true;
    return false;
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
    List<Long> validAnimalIds = new ArrayList<>();
    List<Long> validProductIds = new ArrayList<>();
    List<AnimalPartDto> animalParts = new ArrayList<>();

    switch (input.toLowerCase()) {
      case "1": // Retrieve registration number for all animals involved in a given Product.

        //Show a list of valid Products:
        System.out.println("\nRetrieving registration number for all animals involved in a given Product.\nValid Products are:");
        for (ProductDto product : productRegistrationSystem.getAllProducts()) {
          System.out.println(product);
          validProductIds.add(product.getProductId());
        }

        // Prompt user to select a Product_id to get info for:
        System.out.print("Enter Product_id: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validProductIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        ProductDto product = null;
        try {
          product = productRegistrationSystem.readProduct(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("No Products with this ID found in Repository.");
          break;
        }

        // Retrieve a List of all AnimalPart_ids involved in this Product:
        animalParts = ANIMAL_PART_REGISTRATION_SERVICE.readAnimalPartsByProductId(Long.parseLong(value));

        // Retrieve a List of all Animals involved in each AnimalPart:
        List<Long> animalIdsInvolved = new ArrayList<>();
        for (AnimalPartDto animalPart : animalParts)
          if(animalPart.getAnimalId() > 0)
            animalIdsInvolved.add(animalPart.getAnimalId());

        // Display the List of Animals:
        System.out.println("Id of involved animals are: " + animalIdsInvolved);
        break;


      case "2": // Retrieve all Products a given Animal is involved in.

        //Show a list of valid Products:
        System.out.println("\nRetrieving all Products a given Animal is involved in\nValid Animals are:");
        for (AnimalDto animal : ANIMAL_REGISTRATION_SERVICE.getAllAnimals()) {
          System.out.println(animal);
          validAnimalIds.add(animal.getAnimalId());
        }

        // Prompt user to select an Animal_id to get info for:
        System.out.print("Enter Animal_id: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        AnimalDto animal = null;
        try {
          animal = ANIMAL_REGISTRATION_SERVICE.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("No Animals with this ID found in Repository.");
          break;
        }

        // Retrieve a List of all AnimalPart_ids involved in this Animal:
        animalParts = ANIMAL_PART_REGISTRATION_SERVICE.readAnimalPartsByAnimalId(Long.parseLong(value));

        // Retrieve a List of all Products involved in each AnimalPart:
        List<Long> productIdsInvolved = new ArrayList<>();
        for (AnimalPartDto animalPart : animalParts) {
          if(animalPart.getProductId() > 0)
            productIdsInvolved.add(animalPart.getProductId());
        }


        // Display the List of Products:
        System.out.println("Id of involved products are: " + productIdsInvolved);
        break;

      default:
        System.out.println("Invalid input!");
    }
  }
}
