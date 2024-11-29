package DataServer.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import DataServer.model.persistence.entities.PartType;

/** <p>Defines public static accessible methods used for validating the data integrity of PartType entities,
 * especially useful for validation checks before attempting to commit data to the repository/database</p>
 * */
public class PartTypeValidation
{
  public static void validatePartType(PartType partType) throws DataIntegrityViolationException {
    // PartType cannot be null:
    if(partType == null)
      throw new DataIntegrityViolationException("PartType is null");

    // Validate typeId:
    validateId(partType.getTypeId());

    // Desc weight must not be empty, null or blank:
    validateDesc(partType.getTypeDesc());

    // Validation passed:
  }


  public static void validateId(long typeId) throws DataIntegrityViolationException {
    // Animal_id must be larger than 0:
    if(typeId <= 0)
      throw new DataIntegrityViolationException("typeId is invalid (0 or less)");

    // Validation passed:
  }


  public static void validateDesc(String desc) throws DataIntegrityViolationException {
    // Animal weight must be larger than 0:
    if(desc == null || desc.isBlank())
      throw new DataIntegrityViolationException("desc is empty or blank");

    // Validation passed:
  }
}
