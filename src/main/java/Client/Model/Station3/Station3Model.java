package Client.model.Station3;

import Client.model.BaseModel;
import Client.network.services.gRPC.ProductRegistrationSystem;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.ProductDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.ArrayList;
import java.util.List;

public class Station3Model implements BaseModel
{
  private final List<AnimalPartDto> receivedAnimalPartsList;
  private final ProductRegistrationSystem productRegistrationSystem;

  public Station3Model(ProductRegistrationSystem productRegistrationSystem) {
    this.receivedAnimalPartsList = new ArrayList<>();
    this.productRegistrationSystem = productRegistrationSystem;
  }


  public ProductDto registerNewProduct(List<AnimalPartDto> animalPartContentList, List<TrayDto> receivedPartsFromTrayList) throws CreateFailedException {
    // TODO: Initial validation of data

    // Attempt to register, using gRPC connection:
    ProductDto registeredProduct = productRegistrationSystem.registerNewProduct(animalPartContentList, receivedPartsFromTrayList);

    // Remove the added AnimalParts from the received AnimalParts list:
    this.removeMultipleEntitiesFromReceivedEntityList(registeredProduct.getAnimalPartIdList());

    return registeredProduct;
  }

  public ProductDto readProduct(long productId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return productRegistrationSystem.readProduct(productId);
  }


  public void updateProduct(ProductDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Retrieve the un-modified Product:
    ProductDto unmodifiedDto = this.readProduct(data.getProductId());

    // Attempt to update, using gRPC connection:
    productRegistrationSystem.updateProduct(data);

    // If associated AnimalPart has changed, we need to remove that from the list of valid AnimalParts:
    for (long animalPartId : data.getAnimalPartIdList()){
      if(!unmodifiedDto.getAnimalPartIdList().contains(animalPartId)){
        // This AnimalPart is a new addition. Remove it from the local queue/list:
        this.removeEntityFromReceivedEntityList(animalPartId);
      }
    }
  }


  public boolean removeProduct(long productId) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    return productRegistrationSystem.removeProduct(productId);
  }


  public List<ProductDto> getAllProducts() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    return productRegistrationSystem.getAllProducts();
  }

  @Override public AnimalPartDto readEntityFromReceivedEntityList(long animalPartId) {
    List<AnimalPartDto> registeredAnimalParts = new ArrayList<>(receivedAnimalPartsList);
    for (AnimalPartDto animalPart : registeredAnimalParts) {
      if(animalPart.getPartId() == animalPartId) {
        return animalPart;
      }
    }
    return null;
  }

  @Override public void addEntityToReceivedEntityList(Object obj) {
    if(obj instanceof AnimalPartDto)
      receivedAnimalPartsList.add((AnimalPartDto) obj);
  }

  public List<AnimalPartDto> getAllEntitiesFromReceivedEntityList() {
    return receivedAnimalPartsList;
  }

  private boolean removeEntityFromReceivedEntityList(long id){
    for (AnimalPartDto animalPart : getAllEntitiesFromReceivedEntityList()) {
      if(animalPart.getAnimalId() == id) {
        receivedAnimalPartsList.remove(animalPart);
        return true;
      }
    }
    return false;
  }

  private boolean removeMultipleEntitiesFromReceivedEntityList(List<Long> animalPartIds) {
    boolean success = true;
    for (long id : animalPartIds) {
      if(!removeEntityFromReceivedEntityList(id))
        success = false;
    }
    return success;
  }
}
