package shared.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProductDto
{
  private long productId;
  private final List<Long> animalPartIdList = new ArrayList<>();
  private final List<Long> transferIdList = new ArrayList<>();
  private final List<Long> trayIdList = new ArrayList<>();

  public ProductDto(long id, List<Long> animalPartIdList, List<Long> trayList, List<Long> transferIdList) {
    setProductId(id);
    addAllAnimalPartIds(animalPartIdList);
    addAllTrayIds(trayList);
    addAllTransferIds(transferIdList);
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long product_id) {
    this.productId = product_id;
  }

  public List<Long> getAnimalPartIdList() {
    return Collections.unmodifiableList(animalPartIdList);
  }

  public void addAllAnimalPartIds(List<Long> animalPartIds) {
    if(animalPartIds != null && !animalPartIds.isEmpty()) {
      for (long id : animalPartIds){
        this.addAnimalPartId(id);
      }
    }
  }

  public void addAnimalPartId(long id) {
    if(id != 0 && !animalPartIdList.contains(id))
      animalPartIdList.add(id);
  }

  public boolean removeAnimalPartId(long id) {
    return animalPartIdList.remove(id);
  }

  public void clearAnimalPartIdList() {
    animalPartIdList.clear();
  }

  public List<Long> getTransferIdList() {
    return Collections.unmodifiableList(transferIdList);
  }

  public void addTransferId(long id) {
    if(id != 0)
      transferIdList.add(id);
  }

  public void addAllTransferIds(List<Long> transferIdList) {
    if(transferIdList != null) {
      for (long id : transferIdList)
        this.addTransferId(id);
    }
  }

  public void clearTrayTransferIdList() {
    transferIdList.clear();
  }

  public List<Long> getTrayIdList() {
    return Collections.unmodifiableList(trayIdList);
  }

  public void clearTrayIdList() {
    trayIdList.clear();
  }

  public void addTrayId(long id) {
    if(id != 0 && !trayIdList.contains(id)) {
      trayIdList.add(id);
    }
  }

  public void addAllTrayIds(List<Long> trayIdList) {
    if(trayIdList != null) {
      for (long id : trayIdList)
        this.addTrayId(id);
    }
  }

  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    ProductDto that = (ProductDto) o;
    return getProductId() == that.getProductId() && Objects.equals(getAnimalPartIdList(), that.getAnimalPartIdList()) && Objects.equals(getTransferIdList(), that.getTransferIdList())
        && Objects.equals(getTrayIdList(), that.getTrayIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getProductId(), getAnimalPartIdList(), getTransferIdList(), getTrayIdList());
  }

  @Override public String toString() {
    String returnValue = "product_id: '"
        + getProductId()
        + "', List of associated trayId's: [";

    for (int i = 0; i < getTrayIdList().size(); i++) {
      returnValue += ((getTrayIdList().get(i) != null) ? getTrayIdList().get(i) : "NULL");
      if(i < getTrayIdList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "], List of animalPart_id's in this product: [";

    for (int i = 0; i < getAnimalPartIdList().size(); i++) {
      returnValue += getAnimalPartIdList().get(i);
      if(i < getAnimalPartIdList().size() - 1)
        returnValue += ", ";
    }

    returnValue += "]";

    return returnValue;
  }

  public ProductDto copy() {
    return new ProductDto(getProductId(),
        new ArrayList<>(getAnimalPartIdList()),
        new ArrayList<>(getTrayIdList()),
        new ArrayList<>(getTransferIdList()));
  }
}
