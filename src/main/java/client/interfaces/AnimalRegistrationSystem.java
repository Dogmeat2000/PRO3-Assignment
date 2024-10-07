package client.interfaces;

import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;


/**
 * <p>AnimalRegistrationSystem defines the interface responsible for the client side methods relating to Animal registration and management.</p>
 */
public interface AnimalRegistrationSystem
{
  /** <p>Registers/Creates a new Animal in repository with the given parameters applied.</p>
   * @param weightInKilogram The animals weight in kilogram, decimal units are permitted.
   * @return The created Animal instance.
   * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
   */
  Animal registerNewAnimal (BigDecimal weightInKilogram) throws CreateFailedException;


  /** <p>Looks up any Animal entity with the specified id, in the repository</p>
   * @param animalId A unique identifier assigned to the specific Animal to look up.
   * @return The identified Animal instance.
   * @throws AnimalNotFoundException Thrown if Animal is not be found.
   */
  Animal readAnimal (long animalId) throws AnimalNotFoundException;


  /** <p>Updates the given Animal in the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository to apply the updated information to.</p>
   * @param data An Animal Entity, with proper id, weight, etc. applied to.
   * @throws AnimalNotFoundException Thrown if no matching Animal could be found in the repository.
   * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified Animal entity.
   */
  void updateAnimal (Animal data) throws UpdateFailedException, AnimalNotFoundException;


  /** <p>Deletes the given Animal from the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository.</p>
   * @param animalId A unique identifier assigned to the specific Animal to look up.
   * @return True, if Animal was successfully removed. Otherwise, returns false.
   * @throws AnimalNotFoundException Thrown if no matching Animal could be found in the repository.
   * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Animal entity.
   */
  boolean removeAnimal (long animalId) throws DeleteFailedException, AnimalNotFoundException;


  /** <p>Fetches all Animals from the repository.</p>
   * @return A List containing all Animal entities extracted from the repository.
   * @throws AnimalNotFoundException Thrown if no Animals could be found in the repository.
   */
  List<Animal> getAllAnimals() throws AnimalNotFoundException;
}
