package DataServer.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import DataServer.model.persistence.entities.AnimalPart;
import DataServer.model.persistence.entities.Product;
import DataServer.model.persistence.entities.Tray;
import DataServer.model.persistence.entities.TrayToProductTransfer;

import java.util.List;

/** <p>Defines public static accessible methods used for validating the data integrity of Product entities,
 * especially useful for validation checks before attempting to commit data to the repository/database</p>
 * */
public class ProductValidation
{
  public static void validateProduct(Product product) throws DataIntegrityViolationException {
    // Product cannot be null:
    if(product == null)
      throw new DataIntegrityViolationException("Product is null");

    // Validate productId:
    validateId(product.getProductId());

    // Product must contain at least 1 AnimalPart
    validateContents(product.getAnimalPartList());

    // Product must have received animalParts from at least 1 Tray, this must be reported in a transfer:
    validateTraySupplyList(product.getTraySupplyJoinList());

    // Product must have a transient associated with the Trays that supplied AnimalParts:
    validateTrayList(product.getTraySuppliersList());

    // Validation passed:
  }


  public static void validateProductRegistration(Product product) throws DataIntegrityViolationException {
    // Product cannot be null:
    if(product == null)
      throw new DataIntegrityViolationException("Product is null");

    // Validate productId:
    validateId(product.getProductId());

    // Product must contain at least 1 AnimalPart
    if(product.getAnimalPartList().isEmpty())
      throw new DataIntegrityViolationException("No associated AnimalParts. Must have at least 1 AnimalPart association");

    // Product must have received animalParts from at least 1 Tray:
    if(product.getTraySuppliersList().isEmpty())
      throw new DataIntegrityViolationException("No associated Trays. Must have at least 1 AnimalPart association");

    // Validation passed:
  }


  public static void validateId(long productId) throws DataIntegrityViolationException {
    // productId must be larger than 0:
    if(productId < 0)
      throw new DataIntegrityViolationException("productId is invalid (less then 0)");

    // Validation passed:
  }


  public static void validateContents(List<AnimalPart> contentList) throws DataIntegrityViolationException {
    // Must have received AnimalParts from at least 1 Animal:
    if(contentList.isEmpty())
      throw new DataIntegrityViolationException("List<AnimalPart> is invalid (0 items)");

    // Validation passed:
  }


  public static void validateTraySupplyList(List<TrayToProductTransfer> supplyList) throws DataIntegrityViolationException {
    // Must have a record of at least 1 transfer:
    if(supplyList.isEmpty())
      throw new DataIntegrityViolationException("List<TrayToProductTransfer> is invalid (0 items)");

    // Validation passed:
  }

  public static void validateTrayList(List<Tray> supplyList) throws DataIntegrityViolationException {
    // Must have received AnimalParts from at least 1 Tray:
    if(supplyList.isEmpty())
      throw new DataIntegrityViolationException("List<Tray> is invalid (0 items)");

    // Validation passed:
  }
}
