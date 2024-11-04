package server.controller.rest.service;

import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.controller.rest.adapters.dto_to_java.RestAnimalDto_To_Animal;
import server.controller.rest.adapters.java_to_dto.Animal_ToRest_AnimalDto;
import server.model.persistence.service.AnimalRegistryInterface;
import shared.model.dto.AnimalDto;
import shared.model.entities.Animal;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.rest.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class RestAnimalService
{
  private final AnimalRegistryInterface animalService;
  private final RestAnimalDto_To_Animal restAnimalDtoToAnimalConverter;
  private final Animal_ToRest_AnimalDto animalToRestAnimalDtoConverter;

  @Autowired
  public RestAnimalService(AnimalRegistryInterface animalService,
      RestAnimalDto_To_Animal restAnimalDtoToAnimalConverter,
      Animal_ToRest_AnimalDto animalToRestAnimalDtoConverter) {

    this.animalService = animalService;
    this.restAnimalDtoToAnimalConverter = restAnimalDtoToAnimalConverter;
    this.animalToRestAnimalDtoConverter = animalToRestAnimalDtoConverter;
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AnimalDto registerAnimal(@RequestBody AnimalDto animalDto) throws DtoValidationException,
      DtoConversionException,
      RestPersistenceException,
      RestDataIntegrityViolationException,
      RestInternalServerErrorException {

    try {
      // Convert DTO:
      Animal animal = restAnimalDtoToAnimalConverter.convertToAnimal(animalDto);

      return animalToRestAnimalDtoConverter.convertToAnimalDto(animalService.registerAnimal(animal));

    } catch (PersistenceException e) {
      throw new RestPersistenceException(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      throw new RestDataIntegrityViolationException(e.getMessage());
    } catch (Exception e) {
      throw new RestInternalServerErrorException(e.getMessage());
    }
  }


  @GetMapping("/{animalId}")
  public AnimalDto getAnimal(@PathVariable("animalId") Long animalId) throws DtoValidationException,
      DtoConversionException,
      RestPersistenceException,
      RestNotFoundException,
      RestDataIntegrityViolationException,
      RestInternalServerErrorException
  {

    // Query Animal from repository:
    try {
      // Validate Id:
      if(animalId == null || animalId <= 0L) {
        throw new DataIntegrityViolationException("animalId has invalid format");
      }

      Animal animal = animalService.readAnimal(animalId);

      return animalToRestAnimalDtoConverter.convertToAnimalDto(animal);

    } catch (NotFoundException e) {
      throw new RestNotFoundException(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      throw new RestDataIntegrityViolationException(e.getMessage());
    } catch (PersistenceException e) {
      throw new RestPersistenceException(e.getMessage());
    } catch (Exception e) {
      throw new RestInternalServerErrorException(e.getMessage());
    }
  }


  @GetMapping
  public List<AnimalDto> getAllAnimals(@RequestParam(name = "arrival_date", required = false) String date,
      @RequestParam(name = "origin", required = false) String origin) throws DtoValidationException,
      DtoConversionException,
      RestPersistenceException,
      RestNotFoundException,
      RestDataIntegrityViolationException,
      RestInternalServerErrorException {

    try {
      if(date != null && origin != null) {
        // Both optional query parameters are provided:
        // Validate both query parameters:
        // TODO: Missing implementation

        // Retrieve all animals, where both origin and date equals parameter:
        List<Animal> animals = animalService.getAllAnimals();
        return animalToRestAnimalDtoConverter.convertToAnimalDtoList(animals);
        // TODO: Update, so a single repository query is made, instead.
      } else if (date != null) {
        // Both optional query parameters are provided:
        // Validate query parameters:
        // TODO: Missing implementation

        // Retrieve all animals, where date equals parameter:
        List<Animal> animals = animalService.getAllAnimals();
        return animalToRestAnimalDtoConverter.convertToAnimalDtoList(animals);
        // TODO: Update, so a single repository query is made, instead.
      } else if (origin != null) {
        // Both optional query parameters are provided:
        // Validate query parameters:
        // TODO: Missing implementation

        // Retrieve all animals, where origin equals parameter:
        List<Animal> animals = animalService.getAllAnimals();
        return animalToRestAnimalDtoConverter.convertToAnimalDtoList(animals);
        // TODO: Update, so a single repository query is made, instead.
      } else {
        List<Animal> animals = animalService.getAllAnimals();
        return animalToRestAnimalDtoConverter.convertToAnimalDtoList(animals);
      }
    } catch (NotFoundException e) {
      throw new RestNotFoundException(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      throw new RestDataIntegrityViolationException(e.getMessage());
    } catch (PersistenceException e) {
      throw new RestPersistenceException(e.getMessage());
    } catch (Exception e) {
      throw new RestInternalServerErrorException(e.getMessage());
    }
  }


  @PutMapping("/{animalId}")
  public void updateAnimal(@PathVariable("animalId") Long animalId, @RequestBody AnimalDto animalDto) throws DtoValidationException,
      DtoConversionException,
      RestPersistenceException,
      RestNotFoundException,
      RestDataIntegrityViolationException,
      RestInternalServerErrorException {

    try {
      // Validate:
      if(animalId == animalService.readAnimal(animalId).getId()) {
        // Update:
        Animal receivedAnimal = restAnimalDtoToAnimalConverter.convertToAnimal(animalDto);
        animalService.updateAnimal(receivedAnimal);
      } else {
        throw new Exception("Unexpected error occurred while attempting to update received AnimalDto from repository");
      }
    } catch (NotFoundException e) {
      throw new RestNotFoundException(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      throw new RestDataIntegrityViolationException(e.getMessage());
    } catch (PersistenceException e) {
      throw new RestPersistenceException(e.getMessage());
    } catch (Exception e) {
      throw new RestInternalServerErrorException(e.getMessage());
    }
  }


  @DeleteMapping("/{animalId}")
  public void deleteAnimal(@PathVariable("animalId") Long animalId, @RequestBody AnimalDto animalDto) throws DtoValidationException,
      DtoConversionException,
      RestPersistenceException,
      RestNotFoundException,
      RestDataIntegrityViolationException,
      RestInternalServerErrorException {

    try {
      // Validate:
      if(animalId == animalDto.getAnimalId()) {
        // Delete:
        Animal receivedAnimal = restAnimalDtoToAnimalConverter.convertToAnimal(animalDto);
        animalService.removeAnimal(receivedAnimal);
      } else {
        throw new Exception("Unexpected error occurred while attempting to delete received AnimalDto from repository");
      }
    } catch (NotFoundException e) {
      throw new RestNotFoundException(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      throw new RestDataIntegrityViolationException(e.getMessage());
    } catch (PersistenceException e) {
      throw new RestPersistenceException(e.getMessage());
    } catch (Exception e) {
      throw new RestInternalServerErrorException(e.getMessage());
    }
  }
}
