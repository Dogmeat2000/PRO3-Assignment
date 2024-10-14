package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Tray;
import shared.model.exceptions.NotFoundException;

import java.util.List;

/**
 * <p>Defines the interface responsible for the server side methods relating to Tray registration and management.</p>
 */
public interface TrayRegistryInterface
{
    /** <p>Registers/Creates a new Tray in repository with the given parameters applied.</p>
     * @param data The Tray entity to add to the repository.
     * @return The registered Tray instance.
     * @throws PersistenceException Thrown if registration failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if registration failed, due to non-legal information being assigned to the Tray Entity (i.e. no maxWeight specified)
     */
    Tray registerTray (Tray data) throws PersistenceException, DataIntegrityViolationException;


    /** <p>Looks up a Tray entity with the specified id, in the repository</p>
     * @param trayId A unique identifier assigned to the specific Tray to look up.
     * @return The identified Tray instance.
     * @throws NotFoundException Thrown if Tray is not be found.
     * @throws PersistenceException Thrown if fetching Tray failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if trayId is invalid (i.e. 0 or negative id specified).
     */
    Tray readTray (long trayId) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


    /** <p>Updates the given Tray in the repository. Unique id is extracted from the Tray entity
     * and is used to uniquely identify which Tray entity in the repository to apply the updated information to.</p>
     * @param data A Tray Entity, with proper id, weight, etc. applied to.
     * @return True, if Tray was successfully updated. Otherwise, returns false.
     * @throws NotFoundException Thrown if no matching Tray could be found in the repository.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if Tray contains invalid attributes (i.e. 0 or negative id specified).
     */
    boolean updateTray (Tray data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


    /** <p>Deletes the given Tray from the repository. Unique id is extracted from the Tray entity
     * and is used to uniquely identify which Tray entity in the repository.</p>
     * @param data A Tray Entity, with proper id applied to.
     * @return True, if Tray was successfully removed. Otherwise, returns false.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if Tray contains invalid attributes (i.e. 0 or negative id specified).
     */
    boolean removeTray (Tray data) throws PersistenceException, DataIntegrityViolationException;


    /** <p>Fetches all Trays from the repository.</p>
     * @return A List containing all Tray entities extracted from the repository.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     */
    List<Tray> getAllTrays() throws PersistenceException;
}
