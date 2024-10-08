package client.interfaces;

import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.entities.Tray;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;


// TODO: Update javaDocs!
/**
 * <p>AnimalRegistrationSystem defines the interface responsible for the client side methods relating to Animal registration and management.</p>
 */
public interface AnimalPartRegistrationSystem
{
  /** <p>Registers/Creates a new Animal in repository with the given parameters applied.</p>
   * @param weightInKilogram The animals weight in kilogram, decimal units are permitted.
   * @return The created Animal instance.
   * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
   */
  AnimalPart registerNewAnimalPart (Animal animal, PartType type, Tray tray, BigDecimal weightInKilogram) throws CreateFailedException;


  /** <p>Looks up any Animal entity with the specified id, in the repository</p>
   * @param animalId A unique identifier assigned to the specific Animal to look up.
   * @return The identified Animal instance.
   * @throws NotFoundException Thrown if Animal is not be found.
   */
  AnimalPart readAnimalPart (Animal animal, PartType type, Tray tray) throws NotFoundException;


  /** <p>Updates the given Animal in the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository to apply the updated information to.</p>
   * @param data An Animal Entity, with proper id, weight, etc. applied to.
   * @throws NotFoundException Thrown if no matching Animal could be found in the repository.
   * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified Animal entity.
   */
  void updateAnimalPart (AnimalPart data) throws UpdateFailedException, NotFoundException;


  /** <p>Deletes the given Animal from the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository.</p>
   * @param animalId A unique identifier assigned to the specific Animal to look up.
   * @return True, if Animal was successfully removed. Otherwise, returns false.
   * @throws NotFoundException Thrown if no matching Animal could be found in the repository.
   * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Animal entity.
   */
  boolean removeAnimalPart (AnimalPart data) throws DeleteFailedException, NotFoundException;


  /** <p>Fetches all Animals from the repository.</p>
   * @return A List containing all Animal entities extracted from the repository.
   * @throws NotFoundException Thrown if no Animals could be found in the repository.
   */
  List<AnimalPart> getAllAnimalParts() throws NotFoundException;
}
