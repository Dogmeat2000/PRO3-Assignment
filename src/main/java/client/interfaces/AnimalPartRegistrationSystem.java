package client.interfaces;

import grpc.AnimalId;
import grpc.TrayId;
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


/**
 * <p>AnimalPartRegistrationSystem defines the interface responsible for the client side methods relating to AnimalPart registration and management.</p>
 */
public interface AnimalPartRegistrationSystem
{
  /** <p>Registers/Creates a new AnimalPart in repository with the given parameters applied.</p>
   * @param animal The associated Animal entity from where this AnimalPart was cut.
   * @param type The associated PartType entity describing which type of part this is (i.e. inner thigh)
   * @param tray The associated Tray entity into which this AnimalPart was placed directly after dissection.
   * @param weightInKilogram The AnimalParts weight in kilogram, decimal units are permitted.
   * @return The created AnimalPart instance.
   * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
   */
  AnimalPart registerNewAnimalPart (Animal animal, PartType type, Tray tray, BigDecimal weightInKilogram) throws CreateFailedException;


  /** <p>Looks up any AnimalPart entity with the specified id, in the repository</p>
   * @param animalPartId A unique identifier assigned to the specific AnimalPart to look up.
   * @param animal The associated Animal entity from where this AnimalPart was cut.
   * @param type The associated PartType entity describing which type of part this is (i.e. inner thigh)
   * @param tray The associated Tray entity into which this AnimalPart was placed directly after dissection.
   * @return The identified AnimalPart instance.
   * @throws NotFoundException Thrown if AnimalPart is not be found.
   */
  AnimalPart readAnimalPart (long animalPartId) throws NotFoundException;


  //TODO: Missing javaDocs
  List<AnimalPart> readAnimalPartsByAnimalId(long animalId) throws NotFoundException;


  List<AnimalPart> readAnimalPartsByPartTypeId(long partTypeId) throws NotFoundException;


  List<AnimalPart> readAnimalPartsByProductId(long productId) throws NotFoundException;


  List<AnimalPart> readAnimalPartsByTrayId(long trayId) throws NotFoundException;


  /** <p>Updates the given AnimalPart in the repository. Unique id is extracted from the AnimalPart entity
   * and is used to uniquely identify which AnimalPart entity in the repository to apply the updated information to.</p>
   * @param oldData The unmodified AnimalPart Entity, with proper id, weight, etc. applied to.
   * @param newData the modified AnimalPart Entity, with proper id, weight, etc. applied to.
   * @throws NotFoundException Thrown if no matching AnimalPart could be found in the repository.
   * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified AnimalPart entity.
   */
  void updateAnimalPart (AnimalPart oldData, AnimalPart newData) throws UpdateFailedException, NotFoundException;


  /** <p>Deletes the given AnimalPart from the repository. Unique id is extracted from the AnimalPart entity
   * and is used to uniquely identify which AnimalPart entity in the repository.</p>
   * @param data A unique identifier assigned to the specific AnimalPart to look up.
   * @return True, if AnimalPart was successfully removed. Otherwise, returns false.
   * @throws NotFoundException Thrown if no matching AnimalPart could be found in the repository.
   * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Animal entity.
   */
  boolean removeAnimalPart (AnimalPart data) throws DeleteFailedException, NotFoundException;


  /** <p>Fetches all AnimalParts from the repository.</p>
   * @return A List containing all AnimalPart entities extracted from the repository.
   * @throws NotFoundException Thrown if no AnimalParts could be found in the repository.
   */
  List<AnimalPart> getAllAnimalParts() throws NotFoundException;
}
