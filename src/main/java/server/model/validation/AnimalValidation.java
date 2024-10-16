package server.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Animal;

import java.math.BigDecimal;

/** <p>Defines public static accessible methods used for validating the data integrity of Animal entities,
 * especially useful for validation checks before attempting to commit data to the repository/database</p>
 * */
public class AnimalValidation
{
  public static void validateAnimal(Animal animal) throws DataIntegrityViolationException {
    // Animal cannot be null:
    if(animal == null)
      throw new DataIntegrityViolationException("Animal is null");

    // Validate animal_id:
    validateId(animal.getId());

    // Animal weight must be larger than 0:
    validateWeight(animal.getWeight_kilogram());

    // Validation passed:
  }


  public static void validateId(long animal_id) throws DataIntegrityViolationException {
    // Animal_id must be larger than 0:
    if(animal_id <= 0)
      throw new DataIntegrityViolationException("animal_id is invalid (0 or less)");

    // Validation passed:
  }


  public static void validateWeight(BigDecimal weight) throws DataIntegrityViolationException {
    // Animal weight must be larger than 0:
    if(weight.compareTo(BigDecimal.valueOf(0)) <= 0)
      throw new DataIntegrityViolationException("weight_kilogram is invalid (0 or less)");

    // Validation passed:
  }
}
