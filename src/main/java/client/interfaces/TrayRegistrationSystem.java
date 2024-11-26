package client.interfaces;

import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Defines the interface responsible for the client side methods relating to Tray registration and management.</p>
 */
public interface TrayRegistrationSystem
{
  /** <p>Registers/Creates a new Tray in repository with the given parameters applied.</p>
   * @param maxWeight_kilogram The maximum capacity this Tray can hold, in kilograms.
   * @return The created Tray instance.
   * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
   */
  TrayDto registerNewTray (BigDecimal maxWeight_kilogram) throws CreateFailedException;


  /** <p>Looks up any Tray entity with the specified id, in the repository</p>
   * @param trayId A unique identifier assigned to the specific Tray to look up.
   * @return The identified Tray instance.
   * @throws NotFoundException Thrown if Tray is not be found.
   */
  TrayDto readTray (long trayId) throws NotFoundException;


  /** <p>Updates the given Tray in the repository. Unique id is extracted from the Tray entity
   * and is used to uniquely identify which Tray entity in the repository to apply the updated information to.</p>
   * @param data A Tray Entity, with proper id, desc, etc. applied to.
   * @throws NotFoundException Thrown if no matching Tray could be found in the repository.
   * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified Tray entity.
   */
  void updateTray (TrayDto data) throws UpdateFailedException, NotFoundException;


  /** <p>Deletes the given Tray from the repository. Unique id is extracted from the Tray entity
   * and is used to uniquely identify which Tray entity in the repository.</p>
   * @param typeId A unique identifier assigned to the specific Tray to look up.
   * @return True, if Tray was successfully removed. Otherwise, returns false.
   * @throws NotFoundException Thrown if no matching Tray could be found in the repository.
   * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Tray entity.
   */
  boolean removeTray (long typeId) throws DeleteFailedException, NotFoundException;


  /** <p>Fetches all Trays from the repository.</p>
   * @return A List containing all Tray entities extracted from the repository.
   * @throws NotFoundException Thrown if no Trays could be found in the repository.
   */
  List<TrayDto> getAllTrays() throws NotFoundException;
}
