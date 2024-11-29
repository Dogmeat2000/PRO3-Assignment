package shared.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class AnimalPartDto
{
  private long partId;
  private BigDecimal weight_kilogram;
  private long animalId;
  private long typeId;
  private long trayId;
  private long productId;

  // No-Args constructor for Jackson, JSON parser:
  public AnimalPartDto(){}

  public AnimalPartDto(long partId, BigDecimal weight_kilogram, long typeId, long animalId, long trayId, long productId) {
    setPartId(partId);
    setWeight_kilogram(weight_kilogram);
    setTypeId(typeId);
    setAnimalId(animalId);
    setTrayId(trayId);
    setProductId(productId);
  }

  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }

  public void setWeight_kilogram(BigDecimal weight) {
    this.weight_kilogram = weight;
  }

  public long getPartId() {
    return partId;
  }

  public void setPartId(long part_Id) {
    this.partId = part_Id;
  }

  public long getAnimalId() {
    return animalId;
  }

  public void setAnimalId(long id) {
    this.animalId = id;
  }

  public long getTrayId() {
    return trayId;
  }

  public void setTrayId(long id) {
    this.trayId = id;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long id) {
    this.productId = id;
  }

  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long id) {
    this.typeId = id;
  }

  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    AnimalPartDto that = (AnimalPartDto) o;
    return getPartId() == that.getPartId() && getAnimalId() == that.getAnimalId() && getTypeId() == that.getTypeId() && getTrayId() == that.getTrayId() && getProductId() == that.getProductId()
        && Objects.equals(getWeight_kilogram(), that.getWeight_kilogram());
  }

  @Override public int hashCode() {
    return Objects.hash(getPartId(), getWeight_kilogram(), getAnimalId(), getTypeId(), getTrayId(), getProductId());
  }

  @Override public String toString() {
    return "animalPart_id: '"
        + getPartId()
        + "', weighing: '"
        + getWeight_kilogram()
        + "kg', cut from animal_id: '"
        + ((getAnimalId() != 0) ? getAnimalId() : "NULL")
        + "', with partType_id: '"
        + ((getTypeId() != 0) ? getTypeId() : "NULL")
        + "', stored in tray_id: '"
        + ((getTrayId() != 0) ? getTrayId() : "NULL")
        + "', packed into product_id: '"
        + ((getProductId() != 0) ? getProductId() : "NULL")
        + "'";
  }

  public AnimalPartDto copy() {
    return new AnimalPartDto(
        getPartId(),
        getWeight_kilogram(),
        getAnimalId(),
        getTypeId(),
        getTrayId(),
        getProductId()
    );
  }
}
