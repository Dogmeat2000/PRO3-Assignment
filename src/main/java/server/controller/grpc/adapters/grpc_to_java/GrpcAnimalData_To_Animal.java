package server.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import jakarta.persistence.PersistenceException;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import shared.model.entities.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
@Scope("singleton")
public class GrpcAnimalData_To_Animal
{
  private GrpcAnimalPartData_To_AnimalPart animalPartConverter = null;


  /** <p>Converts database/gRPC compatible AnimalData information into a application compatible Animal entity</p> */
  public Animal convertToAnimal(AnimalData animalData, int maxNestingDepth) throws PersistenceException, DataIntegrityViolationException {

    if (animalData == null || maxNestingDepth < 0)
      return null;

    int currentNestingDepth = maxNestingDepth-1;

    // Lazy initiate the associated AnimalPartConverter as needed:
    if(animalPartConverter == null)
      animalPartConverter = new GrpcAnimalPartData_To_AnimalPart();

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long id = animalData.getAnimalId();
    BigDecimal weight = animalData.getAnimalWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalData.getAnimalWeight());
    String origin = animalData.getOrigin();
    Date arrivalDate = Timestamp.valueOf(animalData.getArrivalDate());
    List<Long> animalPartIdList = new ArrayList<>(animalData.getAnimalPartIdsList());

    // Construct a new Animal entity with the above read attributes set:
    Animal animal = new Animal(id, weight, origin, arrivalDate);
    animal.setAnimalPartIdList(animalPartIdList);

    // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
    try {
      for (AnimalPartData animalPartData : animalData.getAnimalPartListList()) {
        animal.addAnimalPart(animalPartConverter.convertToAnimalPart(animalPartData, currentNestingDepth));
      }
    } catch (NotFoundException e) {
      animal.setAnimalParts(new ArrayList<>());
    }

    return animal;
  }


  public List<Animal> convertToAnimalList(AnimalsData data, int maxNestingDepth) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getAnimalsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<Animal> animalList = new ArrayList<>();
    for (AnimalData animalData : data.getAnimalsList())
      animalList.add(convertToAnimal(animalData, maxNestingDepth));

    // return a new List of Animal entities:
    return animalList;
  }
}
