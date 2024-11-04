package server.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Animal;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.Date;
import java.util.List;

/**
 * <p>AnimalRegistryInterface defines the interface responsible for the server side methods relating to Animal registration and management.</p>
 */
public interface AnimalRegistryInterface
{
  /** <p>Registers/Creates a new Animal in repository with the given parameters applied.</p>
   * @param data The Animal entity to add to the repository.
   * @return The registered Animal instance.
   * @throws PersistenceException Thrown if registration failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   * @throws DataIntegrityViolationException Thrown if registration failed, due to non-legal information being assigned to the Animal Entity (i.e. negative weight specified)
   */
  Animal registerAnimal (Animal data) throws PersistenceException, DataIntegrityViolationException;


  /** <p>Looks up a Animal entity with the specified id, in the repository</p>
   * @param animalId A unique identifier assigned to the specific Animal to look up.
   * @return The identified Animal instance.
   * @throws NotFoundException Thrown if Animal is not be found.
   * @throws PersistenceException Thrown if fetching Animal failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   * @throws DataIntegrityViolationException Thrown if animal_id is invalid (i.e. 0 or negative id specified).
   */
  Animal readAnimal (long animalId) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


  /** <p>Updates the given Animal in the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository to apply the updated information to.</p>
   * @param data An Animal Entity, with proper id, weight, etc. applied to.
   * @return True, if Animal was successfully updated. Otherwise, returns false.
   * @throws NotFoundException Thrown if no matching Animal could be found in the repository.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   * @throws DataIntegrityViolationException Thrown if Animal contains invalid attributes (i.e. 0 or negative id specified).
   */
  boolean updateAnimal (Animal data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


  /** <p>Deletes the given Animal from the repository. Unique id is extracted from the Animal entity
   * and is used to uniquely identify which Animal entity in the repository.</p>
   * @param data An Animal Entity, with proper id applied to.
   * @return True, if Animal was successfully removed. Otherwise, returns false.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   * @throws DataIntegrityViolationException Thrown if Animal contains invalid attributes (i.e. 0 or negative id specified).
   */
  boolean removeAnimal (Animal data) throws PersistenceException, DataIntegrityViolationException;


  /** <p>Fetches all Animals from the repository.</p>
   * @return A List containing all Animal entities extracted from the repository.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   */
  List<Animal> getAllAnimals() throws PersistenceException;


  /** <p>Fetches all Animals from the repository, where the given Origin and Date matches.</p>
   * @param origin A String containing the origin of the Animal (i.e. which farm it came from)
   * @param arrivalDate A Timestamp corresponding to the Date the Animal arrived at the Slaughter House.
   * @return A List containing all Animal entities extracted from the repository, matching given arguments.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   */
  List<Animal> getAllAnimalsByOriginAndDate(String origin, Date arrivalDate) throws PersistenceException;


  /** <p>Fetches all Animals from the repository, where the given Origin matches.</p>
   * @param origin A String containing the origin of the Animal (i.e. which farm it came from)
   * @return A List containing all Animal entities extracted from the repository, matching given arguments.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   */
  List<Animal> getAllAnimalsByOrigin(String origin) throws PersistenceException;


  /** <p>Fetches all Animals from the repository, where the given Date matches.</p>
   * @param arrivalDate A Timestamp corresponding to the Date the Animal arrived at the Slaughter House.
   * @return A List containing all Animal entities extracted from the repository, matching given arguments.
   * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
   */
  List<Animal> getAllAnimalsByDate(Date arrivalDate) throws PersistenceException;
}
