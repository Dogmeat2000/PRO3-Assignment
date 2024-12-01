package shared.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TrayDto
{
  private long trayId;
  private BigDecimal maxWeight_kilogram;
  private BigDecimal weight_kilogram;
  private long trayTypeId;
  private final List<Long> animalPartIdList = new ArrayList<>();
  private final List<Long> transferIdList = new ArrayList<>();
  private final List<Long> productIdList = new ArrayList<>();

  public TrayDto(){}

  public TrayDto(long trayId, BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram, long trayTypeId, List<Long> animalPartIdList) {
    setTrayId(trayId);
    setMaxWeight_kilogram(maxWeight_kilogram);
    setWeight_kilogram(weight_kilogram);
    setTrayTypeId(trayTypeId);
    addAllAnimalPartIds(animalPartIdList);
  }

  public long getTrayId() {
    return trayId;
  }

  public void setTrayId(long tray_id) {
    this.trayId = tray_id;
  }

  public BigDecimal getMaxWeight_kilogram() {
    return maxWeight_kilogram;
  }

  public void setMaxWeight_kilogram(BigDecimal maxCapacity) {
    this.maxWeight_kilogram = maxCapacity;
  }

  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }

  private void setWeight_kilogram(BigDecimal currentWeight) {
    this.weight_kilogram = currentWeight;
  }

  public List<Long> getAnimalPartIdList() {
    return Collections.unmodifiableList(animalPartIdList);
  }

  public void addAnimalPartId(long id) {
    if(id > 0 && !animalPartIdList.contains(id))
      animalPartIdList.add(id);
  }

  public boolean removeAnimalPartId(long id) {
    return animalPartIdList.remove(id);
  }

  public void clearAnimalPartIdList() {
    animalPartIdList.clear();
  }

  public void addAllAnimalPartIds(List<Long> animalPartIds) {
    if(animalPartIds != null) {
      for (long id : animalPartIds){
        this.addAnimalPartId(id);
      }
    }
  }

  public List<Long> getTransferIdList() {
    return Collections.unmodifiableList(transferIdList);
  }

  public void addTransferId(long id) {
    if(id > 0 && !transferIdList.contains(id))
      transferIdList.add(id);
  }

  public void addAllTransferIds(List<Long> transferIds) {
    if(transferIds != null) {
      for (long id : transferIds){
        this.addTransferId(id);
      }
    }
  }

  public boolean removeTransferId(long id) {
    return transferIdList.remove(id);
  }

  public void clearTransferIdList() {
    transferIdList.clear();
  }

  public List<Long> getProductIdList() {
    return Collections.unmodifiableList(productIdList);
  }

  public void addProductId(long id) {
    if(id > 0 && !productIdList.contains(id))
      productIdList.add(id);
  }

  public void addAllProductIds(List<Long> productIds) {
    if(productIds != null) {
      for (long id : productIds){
        this.addProductId(id);
      }
    }
  }

  public boolean removeProductId(long id) {
    return productIdList.remove(id);
  }

  public void clearProductIdList() {
    productIdList.clear();
  }

  public long getTrayTypeId() {
    return trayTypeId;
  }

  public void setTrayTypeId(long id) {
    this.trayTypeId = id;
  }

  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    TrayDto trayDto = (TrayDto) o;
    return getTrayId() == trayDto.getTrayId() && getTrayTypeId() == trayDto.getTrayTypeId() && Objects.equals(getMaxWeight_kilogram(), trayDto.getMaxWeight_kilogram()) && Objects.equals(
        getWeight_kilogram(), trayDto.getWeight_kilogram()) && Objects.equals(getAnimalPartIdList(), trayDto.getAnimalPartIdList()) && Objects.equals(getTransferIdList(), trayDto.getTransferIdList())
        && Objects.equals(getProductIdList(), trayDto.getProductIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getTrayId(), getMaxWeight_kilogram(), getWeight_kilogram(), getTrayTypeId(), getAnimalPartIdList(), getTransferIdList(), getProductIdList());
  }

  @Override public String toString() {
    String returnValue = "tray_id: '"
        + getTrayId()
        + ", maxCapacity: '"
        + getMaxWeight_kilogram()
        + "kg, currentWeight: '"
        + getWeight_kilogram()
        + "kg', only holds partType: '";

    returnValue += (getTrayTypeId() > 0) ? getTrayTypeId() : "NULL";
    returnValue += "', product_id's parts were handed to: [";

    for (int i = 0; i < getProductIdList().size(); i++) {
      returnValue += (getProductIdList().get(i) != null) ? getProductIdList().get(i) : "NULL";
      if(i != getProductIdList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "], animalPart_id's in Tray: [";

    for (int i = 0; i < getAnimalPartIdList().size(); i++) {
      returnValue += getAnimalPartIdList().get(i);
      if(i != getAnimalPartIdList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "], with transferIds: [";

    for (int i = 0; i < getTransferIdList().size(); i++) {
      returnValue += getTransferIdList().get(i);
      if(i != getTransferIdList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "]";

    return returnValue;
  }

  public TrayDto copy() {
    TrayDto trayCopy = new TrayDto(
        trayId,
        getMaxWeight_kilogram(),
        getWeight_kilogram(),
        getTrayTypeId(),
        getAnimalPartIdList()
        );

    trayCopy.addAllProductIds(getProductIdList());
    trayCopy.addAllTransferIds(getTransferIdList());

    return trayCopy;
  }
}
