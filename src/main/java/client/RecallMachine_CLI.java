package client;

import client.interfaces.*;
import client.ui.Model.service.AnimalPartRegistrationSystemImpl;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import client.ui.Model.service.ProductRegistrationSystemImpl;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RecallMachine_CLI /*implements CommandLineRunner*/
{
  private static final AnimalPartRegistrationSystem animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090, 3);
  private static final ProductRegistrationSystem productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090, 3);
  private static final AnimalRegistrationSystem animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090, 3);


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
    List<AnimalPart> animalParts = new ArrayList<>();

    switch (input.toLowerCase()) {
      case "1": // Retrieve registration number for all animals involved in a given Product.

        //Show a list of valid Products:
        System.out.println("\nRetrieving registration number for all animals involved in a given Product.\nValid Products are:");
        for (Product product : productRegistrationSystem.getAllProducts()) {
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
        Product product = null;
        try {
          product = productRegistrationSystem.readProduct(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("No Products with this ID found in Repository.");
          break;
        }

        // Retrieve a List of all AnimalPart_ids involved in this Product:
        animalParts = animalPartRegistrationSystem.readAnimalPartsByProductId(Long.parseLong(value));

        // Retrieve a List of all Animals involved in each AnimalPart:
        List<Long> animalIdsInvolved = new ArrayList<>();
        for (AnimalPart animalPart : animalParts)
          if(animalPart.getAnimal().getId() > 0)
            animalIdsInvolved.add(animalPart.getAnimal().getId());

        // Display the List of Animals:
        System.out.println("Id of involved animals are: " + animalIdsInvolved);
        break;


      case "2": // Retrieve all Products a given Animal is involved in.

        //Show a list of valid Products:
        System.out.println("\nRetrieving all Products a given Animal is involved in\nValid Animals are:");
        for (Animal animal : animalRegistrationSystem.getAllAnimals()) {
          System.out.println(animal);
          validAnimalIds.add(animal.getId());
        }

        // Prompt user to select an Animal_id to get info for:
        System.out.print("Enter Animal_id: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validAnimalIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        Animal animal = null;
        try {
          animal = animalRegistrationSystem.readAnimal(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("No Animals with this ID found in Repository.");
          break;
        }

        // Retrieve a List of all AnimalPart_ids involved in this Animal:
        animalParts = animalPartRegistrationSystem.readAnimalPartsByAnimalId(Long.parseLong(value));

        // Retrieve a List of all Products involved in each AnimalPart:
        List<Long> productIdsInvolved = new ArrayList<>();
        for (AnimalPart animalPart : animalParts) {
          if(animalPart.getProduct().getProductId() > 0)
            productIdsInvolved.add(animalPart.getProduct().getProductId());
        }


        // Display the List of Products:
        System.out.println("Id of involved products are: " + productIdsInvolved);
        break;

      default:
        System.out.println("Invalid input!");
    }
  }
}
