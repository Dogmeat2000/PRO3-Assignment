package server.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.AnimalPart;

import java.math.BigDecimal;

/** <p>Defines public static accessible methods used for validating the data integrity of AnimalPart entities,
 * especially useful for validation checks before attempting to commit data to the repository/database</p>
 * */
public class AnimalPartValidation
{
  public static void validateAnimalPart(AnimalPart animalPart) throws DataIntegrityViolationException {
    // AnimalPart cannot be null:
    if(animalPart == null)
      throw new DataIntegrityViolationException("AnimalPart is null");

    // Validate animalPart_id:
    validateId(animalPart.getPart_id(), animalPart.getAnimal_id(), animalPart.getType_id(), animalPart.getTray_id());

    // AnimalPart weight must be larger than 0:
    validateWeight(animalPart.getWeight_kilogram());

    // Validate associated Animal entity:
    AnimalValidation.validateAnimal(animalPart.getAnimal());

    // Validate associated Tray entity:
    TrayValidation.validateTray(animalPart.getTray());

    // Validate associated PartType entity:
    PartTypeValidation.validatePartType(animalPart.getType());

    // do NOT validate any associated Product entity, since this can be null.
    // Validation passed:
  }


  public static void validateId(long animalPartId, long animal_id, long typeId, long trayId) throws DataIntegrityViolationException {
    // All ids must be larger than 0:
    if(animalPartId <= 0 || animal_id <= 0 || typeId <= 0 || trayId <= 0)
      throw new DataIntegrityViolationException("Invalid ids provided. All ids must have values above 0");

    // Validation passed:
  }


  public static void validateWeight(BigDecimal weight) throws DataIntegrityViolationException {
    // Animal weight must be larger than 0:
    if(weight.compareTo(BigDecimal.valueOf(0)) <= 0)
      throw new DataIntegrityViolationException("weight_kilogram is invalid (0 or less)");

    // Validation passed:
  }
}
