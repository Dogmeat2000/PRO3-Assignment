package DataServer.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import DataServer.model.persistence.entities.AnimalPart;
import DataServer.model.persistence.entities.Tray;

import java.math.BigDecimal;

/** <p>Defines public static accessible methods used for validating the data integrity of Tray entities,
 * especially useful for validation checks before attempting to commit data to the repository/database</p>
 * */
public class TrayValidation
{
  public static void validateTray(Tray tray) throws DataIntegrityViolationException {
    // Tray cannot be null:
    if(tray == null)
      throw new DataIntegrityViolationException("Tray is null");

    // Validate trayId:
    validateId(tray.getTrayId());

    // Tray maxWeight must be 0 or larger:
    validateWeight(tray.getMaxWeight_kilogram());

    // Tray weight must be 0 or larger:
    validateWeight(tray.getWeight_kilogram());

    // Tray must have 0 or more available space:
    if(tray.getMaxWeight_kilogram().compareTo(tray.getWeight_kilogram()) < 0)
      throw new DataIntegrityViolationException("Available tray weight is negative (" + tray.getMaxWeight_kilogram().compareTo(tray.getWeight_kilogram()) + "kg)");

    // All contents must be of the same PartType:
    for (AnimalPart animalPart : tray.getAnimalPartList()) {
      if(!animalPart.getType().equals(tray.getTrayType()))
        throw new DataIntegrityViolationException("AnimalPart PartType mismatch with TrayType. Trays can only transport AnimalParts of the same PartType.");
    }

    // Validation passed:
  }


  public static void validateId(long trayId) throws DataIntegrityViolationException {
    // trayId must be larger than 0:
    if(trayId <= 0)
      throw new DataIntegrityViolationException("trayId is invalid (0 or less)");

    // Validation passed:
  }


  public static void validateWeight(BigDecimal weight) throws DataIntegrityViolationException {
    // Tray weight must be larger than, or equal to 0:
    if(weight.compareTo(BigDecimal.valueOf(0)) < 0)
      throw new DataIntegrityViolationException("weight_kilogram is invalid (less than zero)");

    // Validation passed:
  }
}
