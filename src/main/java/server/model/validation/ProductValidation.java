package server.model.validation;

import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.entities.TrayToProductTransfer;

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
    validateContents(product.getContentList());

    // Product must have received animalParts from at least 1 Tray:
    validateTraySupplyList(product.getTraySupplyJoinList());

    // Validation passed:
  }


  public static void validateId(long productId) throws DataIntegrityViolationException {
    // productId must be larger than 0:
    if(productId <= 0)
      throw new DataIntegrityViolationException("productId is invalid (0 or less)");

    // Validation passed:
  }


  public static void validateContents(List<AnimalPart> contentList) throws DataIntegrityViolationException {
    // Must have received AnimalParts from at least 1 Animal:
    if(contentList.isEmpty())
      throw new DataIntegrityViolationException("List<AnimalPart> is invalid (0 items)");

    // Validation passed:
  }


  public static void validateTraySupplyList(List<TrayToProductTransfer> supplyList) throws DataIntegrityViolationException {
    // Must have received AnimalParts from at least 1 Tray:
    if(supplyList.isEmpty())
      throw new DataIntegrityViolationException("List<TrayToProductTransfer> is invalid (0 items)");

    // Validation passed:
  }
}
