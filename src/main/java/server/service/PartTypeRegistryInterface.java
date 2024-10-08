package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Animal;
import shared.model.entities.PartType;
import shared.model.exceptions.NotFoundException;

import java.util.List;

/**
 * <p>Defines the interface responsible for the server side methods relating to PartType registration and management.</p>
 */
public interface PartTypeRegistryInterface
{
    /** <p>Registers/Creates a new PartType in repository with the given parameters applied.</p>
     * @param data The PartType entity to add to the repository.
     * @return The registered PartType instance.
     * @throws PersistenceException Thrown if registration failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if registration failed, due to non-legal information being assigned to the PartType Entity (i.e. no desc specified)
     */
    PartType registerPartType (PartType data) throws PersistenceException, DataIntegrityViolationException;


    /** <p>Looks up a PartType entity with the specified id, in the repository</p>
     * @param typeId A unique identifier assigned to the specific PartType to look up.
     * @return The identified PartType instance.
     * @throws NotFoundException Thrown if PartType is not be found.
     * @throws PersistenceException Thrown if fetching PartType failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if typeId is invalid (i.e. 0 or negative id specified).
     */
    PartType readPartType (long typeId) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


    /** <p>Updates the given PartType in the repository. Unique id is extracted from the PartType entity
     * and is used to uniquely identify which PartType entity in the repository to apply the updated information to.</p>
     * @param data An PartType Entity, with proper id, desc, etc. applied to.
     * @return True, if PartType was successfully updated. Otherwise, returns false.
     * @throws NotFoundException Thrown if no matching PartType could be found in the repository.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if PartType contains invalid attributes (i.e. 0 or negative id specified).
     */
    boolean updatePartType (PartType data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;


    /** <p>Deletes the given PartType from the repository. Unique id is extracted from the PartType entity
     * and is used to uniquely identify which PartType entity in the repository.</p>
     * @param data An PartType Entity, with proper id applied to.
     * @return True, if PartType was successfully removed. Otherwise, returns false.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     * @throws DataIntegrityViolationException Thrown if PartType contains invalid attributes (i.e. 0 or negative id specified).
     */
    boolean removePartType (PartType data) throws PersistenceException, DataIntegrityViolationException;


    /** <p>Fetches all PartType from the repository.</p>
     * @return A List containing all PartType entities extracted from the repository.
     * @throws PersistenceException Thrown if update failed, due to system/persistence issues (i.e. Repository is offline, etc.)
     */
    List<PartType> getAllPartTypes() throws PersistenceException;
}
