package Client.Station1_AnimalRegistration.viewModel;

import Client.Station1_AnimalRegistration.model.Station1Model;
import shared.model.dto.AnimalDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Station1ViewModel
{
  private final Station1Model station1Model;

  public Station1ViewModel(Station1Model station1Model){
    this.station1Model = station1Model;
  }


  private boolean inputContainsLegalCommand(String input) {
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

  public boolean validateCommand(String input) {
    return input != null && !input.isBlank() & !input.isEmpty() && inputContainsLegalCommand(input);
  }

  private boolean validateBigDecimalInput(String input) {
    try {
      new BigDecimal(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  private boolean validateLongInput(String input) {
    try {
      Long.parseLong(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUserInput() {
    Scanner input = new Scanner(System.in);
    return input.nextLine();
  }

  public void performCommand(String input) {
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
            AnimalDto animal = station1Model.registerNewAnimal(newAnimalWeight, value, today);
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
            if(station1Model.removeAnimal(Long.parseLong(value)))
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
            oldAnimal = station1Model.readAnimal(Long.parseLong(value));
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
            station1Model.updateAnimal(newAnimal);
            System.out.println("Implemented updated Animal [" + newAnimal + "] in Database!");
          } catch (UpdateFailedException | NotFoundException e) {
            e.printStackTrace();
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
            AnimalDto animal = station1Model.readAnimal(Long.parseLong(value));
            System.out.println("Found [" + animal + "]");
          } catch (NotFoundException e) {
            System.out.println("No Animals found in Database!");
          }
        break;


      case "viewall":
        System.out.println("Retrieving all Animals from Database: ");
        try {
          List<AnimalDto> animals = station1Model.getAllAnimals();

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
