package server.controller.rest.adapters.dto_to_java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.model.persistence.service.AnimalPartRegistryInterface;
import shared.model.dto.AnimalDto;
import server.model.persistence.entities.Animal;
import server.model.persistence.entities.AnimalPart;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.rest.DtoConversionException;
import shared.model.exceptions.rest.DtoValidationException;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a REST Data Transfer Object (DTO) into application compatible entities</p>*/
@Component
@Scope("singleton")
public class RestAnimalDto_To_Animal
{
  private final AnimalPartRegistryInterface animalPartService;


  @Autowired
  public RestAnimalDto_To_Animal(AnimalPartRegistryInterface animalPartService){
    this.animalPartService = animalPartService;
  }


  /** <p>Converts REST compatible AnimalDto into a application compatible Animal entity</p> */
  public Animal convertToAnimal(AnimalDto animalDto) throws DtoValidationException, DtoConversionException {

    // Validate:
    if (animalDto == null)
      throw new DtoValidationException("animalDto cannot be null");

    // Convert:
    try {
      Animal animal = new Animal();
      animal.setId(animalDto.getAnimalId());
      animal.setWeight_kilogram(animalDto.getWeight_kilogram());
      animal.setOrigin(animalDto.getOrigin());
      animal.setArrivalDate(animalDto.getArrivalDate());
      //animal.setAnimalPartIdList(animalDto.getAnimalPartIdList());
      animal.setAnimalParts(new ArrayList<>());

      // Populate Animal entity with the proper associations:
      try {
        for (AnimalPart animalPart : animalPartService.readAnimalPartsByAnimalId(animalDto.getAnimalId())) {
          animal.addAnimalPart(animalPart);
        }
      } catch (NotFoundException ignored) {}

      return animal;
    } catch (Exception e) {
      throw new DtoConversionException(e.getMessage());
    }
  }


  /** <p>Converts a List of AnimalDtos into a List of the application compatible Animal format</p>*/
  public List<Animal> convertToAnimalList(List<AnimalDto> data) throws DtoValidationException, DtoConversionException {

    // Validate:
    if(data == null)
      throw new DtoValidationException("AnimalDto List cannot be null!");

    // Return an empty list, if received list is empty.
    if(data.isEmpty())
      return new ArrayList<>();

    // Convert:
    try {
      List<Animal> animals = new ArrayList<>();
      for (AnimalDto animalDto : data) {
        animals.add(convertToAnimal(animalDto));
      }
      return animals;
    } catch (Exception e) {
      throw new DtoConversionException(e.getMessage());
    }
  }
}
