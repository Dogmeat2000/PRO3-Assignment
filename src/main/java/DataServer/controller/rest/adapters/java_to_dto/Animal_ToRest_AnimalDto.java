package DataServer.controller.rest.adapters.java_to_dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.dto.AnimalDto;
import DataServer.model.persistence.entities.Animal;
import shared.model.exceptions.rest.DtoConversionException;
import shared.model.exceptions.rest.DtoValidationException;

import java.util.ArrayList;
import java.util.List;

/**<p>Responsible for converting a application entities into a REST compatible formats</p>*/
@Component
@Scope("singleton")
public class Animal_ToRest_AnimalDto
{
  /** <p>Converts an Animal entity into a REST compatible AnimalDto format</p> */
  public AnimalDto convertToAnimalDto(Animal animal) throws DtoValidationException, DtoConversionException {

    if (animal == null)
      throw new DtoValidationException("animal is null");

    try {
      return new AnimalDto(animal.getId(), animal.getWeight_kilogram(), animal.getOrigin(), animal.getArrivalDate(), animal.getAnimalPartIdList());
    } catch (Exception e) {
      throw new DtoConversionException(e.getMessage());
    }
  }


  /** <p>Converts a List of Animals into the REST compatible AnimalDto format</p>*/
  public List<AnimalDto> convertToAnimalDtoList(List<Animal> animals) throws DtoValidationException, DtoConversionException {

    // Validate:
    if(animals == null) {
      throw new DtoValidationException("animal List is null");
    } else if(animals.isEmpty()) {
      return new ArrayList<>();
    }

    // Convert:
    try {
      List<AnimalDto> animalDtos = new ArrayList<>();
      for (Animal animal : animals) {
        animalDtos.add(convertToAnimalDto(animal));
      }
      return animalDtos;

    } catch (Exception e) {
      throw new DtoConversionException(e.getMessage());
    }
  }
}
