package client.interfaces;

import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.entities.Tray;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

/**
 * <p>Defines the interface responsible for the client side methods relating to Product registration and management.</p>
 */
public interface ProductRegistrationSystem
{
    /** <p>Registers/Creates a new Product in repository with the given parameters applied.</p>
     * @param animalPartContentList A List of AnimalParts to register to this Product.
     * @param receivedPartsFromTrayList A List of references to the Trays from which this Product received animalParts.
     * @return The created Product instance.
     * @throws CreateFailedException Thrown if creation/registration fails, for any reason.
     */
    Product registerNewProduct (List<AnimalPart> animalPartContentList, List<Tray> receivedPartsFromTrayList) throws CreateFailedException;


    /** <p>Looks up any Product entity with the specified id, in the repository</p>
     * @param productId A unique identifier assigned to the specific Product to look up.
     * @return The identified Product instance.
     * @throws NotFoundException Thrown if Product is not be found.
     */
    Product readProduct (long productId) throws NotFoundException;


    /** <p>Updates the given Product in the repository. Unique id is extracted from the Product entity
     * and is used to uniquely identify which Product entity in the repository to apply the updated information to.</p>
     * @param data A Product Entity, with proper id, contents, etc. applied to.
     * @throws NotFoundException Thrown if no matching Product could be found in the repository.
     * @throws UpdateFailedException Thrown if an error occurred while applying the updates to the specified Product entity.
     */
    void updateProduct (Product data) throws UpdateFailedException, NotFoundException;


    /** <p>Deletes the given Product from the repository. Unique id is extracted from the Product entity
     * and is used to uniquely identify which Product entity in the repository.</p>
     * @param productId A unique identifier assigned to the specific Product to look up.
     * @return True, if Product was successfully removed. Otherwise, returns false.
     * @throws NotFoundException Thrown if no matching Product could be found in the repository.
     * @throws DeleteFailedException Thrown if an error occurred while deleting the specified Product entity.
     */
    boolean removeProduct (long productId) throws DeleteFailedException, NotFoundException;


    /** <p>Fetches all Products from the repository.</p>
     * @return A List containing all Product entities extracted from the repository.
     * @throws NotFoundException Thrown if no Products could be found in the repository.
     */
    List<Product> getAllProducts() throws NotFoundException;
}
