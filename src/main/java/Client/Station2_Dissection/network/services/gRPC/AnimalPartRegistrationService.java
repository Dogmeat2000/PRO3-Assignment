package Client.Station2_Dissection.network.services.gRPC;

import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.PartTypeDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;


/**
 * <p>AnimalPartRegistrationService defines the interface responsible for the client side methods relating to AnimalPart registration and management.</p>
 */
public interface AnimalPartRegistrationService
{
  /** <p>Registers/Creates a new AnimalPart in repository with the given parameters applied.</p>
   * @param animal The associated Animal entity from where this AnimalPart was cut.
   * @param type The associated PartType entity describing which type of part this is (i.e. inner thigh)
   * @param tray The associated Tray entity into which this AnimalPart was placed directly after dissection.
   * @param weightInKilogram The AnimalParts weight in kilogram, decimal units are permitted.
   * @return The created AnimalPart instance.
   * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
   */
  AnimalPartDto registerNewAnimalPart (AnimalDto animal, PartTypeDto type, TrayDto tray, BigDecimal weightInKilogram) throws CreateFailedException;


  /** <p>Looks up any AnimalPart entity with the specified id, in the repository</p>
   * @param animalPartId A unique identifier assigned to the specific AnimalPart to look up.
   * @return The identified AnimalPart instance.
   * @throws NotFoundException Thrown if AnimalPart is not be found.
   */
  AnimalPartDto readAnimalPart (long animalPartId) throws NotFoundException;


  //TODO: Missing javaDocs
  List<AnimalPartDto> readAnimalPartsByAnimalId(long animalId) throws NotFoundException;


  List<AnimalPartDto> readAnimalPartsByPartTypeId(long partTypeId) throws NotFoundException;


  List<AnimalPartDto> readAnimalPartsByProductId(long productId) throws NotFoundException;


  List<AnimalPartDto> readAnimalPartsByTrayId(long trayId) throws NotFoundException;


  /** <p>Updates the given AnimalPart in the repository. Unique id is extracted from the AnimalPart entity
   * and is used to uniquely identify which AnimalPart entity in the repository to apply the updated information to.</p>
   * @param data The modified AnimalPart Entity, with proper id, weight, etc. applied to.
   * @throws NotFoundException Thrown if no matching AnimalPart could be found in the repository.
   * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified AnimalPart entity.
   */
  void updateAnimalPart (AnimalPartDto data) throws UpdateFailedException, NotFoundException;


  /** <p>Deletes the given AnimalPart from the repository. Unique id is extracted from the AnimalPart entity
   * and is used to uniquely identify which AnimalPart entity in the repository.</p>
   * @param data A unique identifier assigned to the specific AnimalPart to look up.
   * @return True, if AnimalPart was successfully removed. Otherwise, returns false.
   * @throws NotFoundException Thrown if no matching AnimalPart could be found in the repository.
   * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Animal entity.
   */
  boolean removeAnimalPart (AnimalPartDto data) throws DeleteFailedException, NotFoundException;


  /** <p>Fetches all AnimalParts from the repository.</p>
   * @return A List containing all AnimalPart entities extracted from the repository.
   * @throws NotFoundException Thrown if no AnimalParts could be found in the repository.
   */
  List<AnimalPartDto> getAllAnimalParts() throws NotFoundException;
}
