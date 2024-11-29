package DataServer.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import DataServer.model.persistence.entities.Animal;
import DataServer.model.persistence.service.AnimalPartService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
public class GrpcAnimalData_To_Animal
{
  private final AnimalPartService animalPartService;

  @Autowired
  public GrpcAnimalData_To_Animal(AnimalPartService animalPartService) {
    this.animalPartService = animalPartService;
  }

  /** <p>Converts gRPC compatible AnimalData information into a application compatible Animal entity</p> */
  public Animal convertToAnimal(AnimalData animalData) throws PersistenceException, DataIntegrityViolationException {

    if (animalData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long id = animalData.getAnimalId();
    BigDecimal weight = animalData.getAnimalWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalData.getAnimalWeight());
    String origin = animalData.getOrigin();
    Date arrivalDate;
    if(!animalData.getArrivalDate().isEmpty()) {
      try {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        arrivalDate = formatter.parse(animalData.getArrivalDate());
      } catch (ParseException e) {
        e.printStackTrace();
        throw new DataIntegrityViolationException("Failed to convert Animal registration Date.");
      }

    } else
      arrivalDate = null;

    // Construct a new Animal entity with the above read attributes set:
    Animal animal = new Animal(id, weight, origin, arrivalDate);

    // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
    try {
      for (AnimalPartId animalPartId : animalData.getAnimalPartIdsList()) {
        long _id = GrpcId_To_LongId.ConvertToLongId(animalPartId);
        animalPartService.readAnimalPart(_id);
      }
    } catch (NotFoundException e) {
      animal.setAnimalParts(new ArrayList<>());
    }

    return animal;
  }

  public List<Animal> convertToAnimalList(AnimalsData data) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getAnimalsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalsData to a java compatible list by iterating through each entry and running the method previously declared:
    List<Animal> animalList = new ArrayList<>();
    for (AnimalData animalData : data.getAnimalsList())
      animalList.add(convertToAnimal(animalData));

    // return a new List of Animal entities:
    return animalList;
  }
}
