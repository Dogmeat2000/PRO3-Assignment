package Client.common.services.gRPC;

import shared.model.dto.PartTypeDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

/**
 * <p>Defines the interface responsible for the client side methods relating to PartType registration and management.</p>
 */
public interface PartTypeRegistrationSystem
{
    /** <p>Registers/Creates a new Part Type in repository with the given parameters applied.</p>
     * @param desc The description that applies to this part type (i.e. Pig foot).
     * @return The created PartType instance.
     * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
     */
    PartTypeDto registerNewPartType (String desc) throws CreateFailedException;


    /** <p>Looks up any PartType entity with the specified id, in the repository</p>
     * @param typeId A unique identifier assigned to the specific PartType to look up.
     * @return The identified PartType instance.
     * @throws NotFoundException Thrown if PartType is not be found.
     */
    PartTypeDto readPartType (long typeId) throws NotFoundException;


    /** <p>Updates the given PartType in the repository. Unique id is extracted from the PartType entity
     * and is used to uniquely identify which PartType entity in the repository to apply the updated information to.</p>
     * @param data A PartType Entity, with proper id, desc, etc. applied to.
     * @throws NotFoundException Thrown if no matching PartType could be found in the repository.
     * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified PartType entity.
     */
    void updatePartType (PartTypeDto data) throws UpdateFailedException, NotFoundException;


    /** <p>Deletes the given PartType from the repository. Unique id is extracted from the PartType entity
     * and is used to uniquely identify which PartType entity in the repository.</p>
     * @param typeId A unique identifier assigned to the specific PartType to look up.
     * @return True, if PartType was successfully removed. Otherwise, returns false.
     * @throws NotFoundException Thrown if no matching PartType could be found in the repository.
     * @throws DeleteFailedException Thrown if an error occurred while deleting the specified PartType entity.
     */
    boolean removePartType (long typeId) throws DeleteFailedException, NotFoundException;


    /** <p>Fetches all PartTypes from the repository.</p>
     * @return A List containing all PartType entities extracted from the repository.
     * @throws NotFoundException Thrown if no PartTypes could be found in the repository.
     */
    List<PartTypeDto> getAllPartTypes() throws NotFoundException;
}
