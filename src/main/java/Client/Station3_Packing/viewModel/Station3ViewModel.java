package Client.Station3_Packing.viewModel;

import Client.Station3_Packing.model.Station3Model;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.ProductDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Station3ViewModel
{
  private final Station3Model station3Model;

  public Station3ViewModel(Station3Model station3Model){
    this.station3Model = station3Model;
  }

  public String getUserInput() {
    Scanner input = new Scanner(System.in);
    return input.nextLine();
  }


  public boolean validateCommand(String input) {
    return input != null && !input.isBlank() & !input.isEmpty() && inputContainsLegalCommand(input);
  }

  private boolean inputContainsLegalCommand(String input) {
    List<String> legalCommands = new ArrayList<>();
    legalCommands.add("Add");
    legalCommands.add("Remove");
    legalCommands.add("Update");
    legalCommands.add("ViewOne");
    legalCommands.add("ViewAll");

    for (String command : legalCommands)
      if(input.toLowerCase().contains(command.toLowerCase()) && input.length() == command.length())
        return true;
    return false;
  }

  private boolean validateLongInput(String input) {
    try {
      Long.parseLong(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void performCommand(String input) {
    String value;
    List<Long> validPartIds = new ArrayList<>();
    List<Long> validProductIds = new ArrayList<>();

    switch (input.toLowerCase()) {
      case "add":
        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        //validPartIds.clear();
        for (AnimalPartDto animalPart : station3Model.getAllEntitiesFromReceivedEntityList()) {
          if(animalPart.getProductId() == 0) {
            // This AnimalPart is not already packed into a Product.
            System.out.println(animalPart);
            validPartIds.add(animalPart.getPartId());
          }
        }

        // Prompt user to enter the id of AnimalPart to add to Product:
        System.out.print("Enter id of initial AnimalPart to place inside the new Product: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validPartIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        AnimalPartDto parentAnimalPart = null;
        try {
          parentAnimalPart = station3Model.readEntityFromReceivedEntityList(Long.parseLong(value));
        } catch (NotFoundException e) {
          System.out.println("Invalid Animal Id!");
          break;
        }
        List<AnimalPartDto> partsToPack = new ArrayList<>();
        partsToPack.add(parentAnimalPart);

        // Read the Tray associated with the above AnimalParts:
        List<TrayDto> traysReceivedFrom = new ArrayList<>();
        for (AnimalPartDto animalPart : partsToPack){
          try {
            traysReceivedFrom.add(station3Model.readTray(animalPart.getTrayId()));
          } catch (NotFoundException e) {
            System.out.println("Invalid Tray!");
            break;
          }
        }

        // Save the Product to the database:
        try {
          /*ProductDto product =*/ station3Model.registerNewProduct(partsToPack, traysReceivedFrom);
          //System.out.println("Added [" + product + "] to Database!");
        } catch (CreateFailedException e) {
          e.printStackTrace();
          System.out.println("Product Registration failed, " + e.getMessage());
          break;
        }
        break;


      case "remove":
        //Show a list of valid Products: //TODO: Refine this functionality!
        System.out.println("NOT PROPERLY IMPLEMENTED YET");
        /*System.out.println("\nValid Products are:");
        validProductIds.clear();
        for (Product product : STATION_3_MODEL.getAllProducts()) {
          System.out.println(product);
          validProductIds.add(product.getProductId());
        }

        // Prompt user to enter the product_id to remove:
        System.out.print("Enter productId to remove: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validProductIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long product_idToRemove = Long.parseLong(value);

        // Remove the Product
        try {
          Product productToRemove = STATION_3_MODEL.readProduct(product_idToRemove);
          if(STATION_3_MODEL.removeProduct(productToRemove.getProductId()))
            System.out.println("Removed Product with ProductId '" + product_idToRemove + "' from Database!");
        } catch (DeleteFailedException | NotFoundException e) {
          e.printStackTrace();
          System.out.println("ERROR: Could not remove designated AnimalPart from Database");
          break;
        }
        break;*/


      case "update":
        //Show a list of valid Products: //TODO: Refine this functionality!
        System.out.println("NOT PROPERLY IMPLEMENTED YET");
        /*System.out.println("\nValid Products are:");
        for (Product product : STATION_3_MODEL.getAllProducts()) {
          System.out.println(product);
          validProductIds.add(product.getProductId());
        }

        // Prompt user to enter the product_id to update:
        System.out.print("Enter product_id for Product to update: ");
        value = getUserInput();
        if(!validateLongInput(value) || !validProductIds.contains(Long.parseLong(value))){
          System.out.println("Invalid input!");
          break;
        }
        long product_idToUpdate = Long.parseLong(value);

        // Read the Product to modify from the database:
        Product modifiedProduct = null;
        try {
          modifiedProduct = STATION_3_MODEL.readProduct(product_idToUpdate);
          if(modifiedProduct != null)
            System.out.println("You are modifying Product\n: " + modifiedProduct);
        } catch (NotFoundException e) {
          e.printStackTrace();
          System.out.println("ERROR: Could not retrieve original Product from Database. It no longer exists in database.");
          break;
        }
        Product oldProduct = modifiedProduct.copy();

        //Show a list of valid AnimalParts:
        System.out.println("\nValid AnimalParts are:");
        validPartIds.clear();
        for (AnimalPart animalPart : ANIMAL_PART_REGISTRATION_SERVICE.getAllAnimalParts()) {
          if(animalPart.getProduct() == null || animalPart.getProduct().getProductId() == 0 || animalPart.getProduct().getProductId() == modifiedProduct.getProductId()) {
            // This AnimalPart is not already packed into a Product, or already belongs to this Product.
            System.out.println(animalPart);
            validPartIds.add(animalPart.getPart_id());
          }
        }

        // Prompt user for modified values:
        // Prompt user for a list of AnimalPart ids to add:
        System.out.println("Please enter part_ids to pack in this product (use ';' between each part_id to add more)\n(Old part_id list is: [" + oldProduct.getAnimalPartIdList() + "]");

        value = getUserInput();

        // Remove any spaces from input:
        value = value.replace(" ", "");

        // Extract a list of part_ids:
        String[] partIds = value.split(";");

        // Quick initial validation of each partId:
        for (String partId : partIds) {
          if(!validateLongInput(partId)){
            System.out.println("Invalid input!");
            break;
          }
        }

        // Attempt to read all AnimalParts from repository:
        List<AnimalPart> animalPartsToPack = new ArrayList<>();
        for (String partId : partIds) {
          try {
            animalPartsToPack.add(ANIMAL_PART_REGISTRATION_SERVICE.readAnimalPart(Long.parseLong(partId)));
          } catch (NotFoundException e) {
            System.out.println("Invalid Part_Id!");
            break;
          }
        }
        modifiedProduct.getContentList().clear();
        for (AnimalPart animalPart : animalPartsToPack)
          modifiedProduct.addAnimalPart(animalPart);


        // Read the Trays associated with the above AnimalParts:
        List<Tray> traysAssociatedWithPartsToPack = new ArrayList<>();
        for (AnimalPart animalPart : animalPartsToPack){
          try {
            traysAssociatedWithPartsToPack.add(trayRegistrationSystem.readTray(animalPart.getTray().getTrayId()));
          } catch (NotFoundException e) {
            System.out.println("Invalid Tray!");
            break;
          }
        }
        modifiedProduct.getTraySuppliersList().clear();
        for (Tray tray : traysAssociatedWithPartsToPack)
          modifiedProduct.getTraySuppliersList().add(tray);

        // Save the modified Product to the database:
        try {
          STATION_3_MODEL.updateProduct(modifiedProduct);
          System.out.println("Added [" + modifiedProduct + "] to Database!");
        } catch (UpdateFailedException e) {
          e.printStackTrace();
          System.out.println("Product Update failed, " + e.getMessage());
          break;
        }
        break;*/


      case "viewone":
        // Prompt user to enter the Product_id to view:
        System.out.print("Enter productId to belonging to the Product you want to view: ");
        value = getUserInput();
        if(!validateLongInput(value)){
          System.out.println("Invalid input!");
          break;
        }
        long productId = Long.parseLong(value);

        // Read the Product to from the database:
        ProductDto product;
        try {
          product = station3Model.readProduct(productId);
          System.out.println("Found [" + product + "]");
        } catch (NotFoundException e) {
          System.out.println("ERROR: Could not retrieve original Product from Database. It no longer exists in database.");
          break;
        }
        break;


      case "viewall":
        //Show a list of valid Products:
        System.out.println("\nRetrieving all Products from Database: ");
        try {
          List<ProductDto> productsFound = station3Model.getAllProducts();
          if(!productsFound.isEmpty()){
            for (ProductDto localProduct : productsFound) {
              System.out.println(localProduct);
            }
          } else {
            throw new NotFoundException("");
          }

        } catch (NotFoundException e) {
          System.out.println("Could not find any Products in repository");
        }
        break;

      default:
        System.out.println("Invalid input!");
    }
  }
}
