package shared.model.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tray
{
  private PartType partType;
  private BigDecimal maxCapacity;
  private BigDecimal currentWeight;
  private String tray_id;
  private List<AnimalPart> contents;


  public Tray(PartType partType, BigDecimal maxCapacity) {
    setPartType(partType);
    setMaxCapacity(maxCapacity);
    setTray_id("NotSpecified");
    setCurrentWeight(BigDecimal.valueOf(0));
    this.contents = new ArrayList<>();
  }


  public PartType getPartType() {
    return partType;
  }


  public void setPartType(PartType partType) {
    this.partType = partType;
  }


  public BigDecimal getMaxCapacity() {
    return maxCapacity;
  }


  public void setMaxCapacity(BigDecimal maxCapacity) {
    this.maxCapacity = maxCapacity;
  }


  public BigDecimal getCurrentWeight() {
    return currentWeight;
  }


  public void setCurrentWeight(BigDecimal currentWeight) {
    this.currentWeight = currentWeight;
  }


  public String getTray_id() {
    return tray_id;
  }


  public void setTray_id(String tray_id) {
    this.tray_id = tray_id;
  }


  public List<AnimalPart> getContents() {
    return contents;
  }


  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getTray_id(), ((Tray) o).getTray_id())
        && Objects.equals(getCurrentWeight(), ((Tray) o).getCurrentWeight())
        && Objects.equals(getMaxCapacity(), ((Tray) o).getMaxCapacity())
        && Objects.equals(getPartType(), ((Tray) o).getPartType())
        && Objects.equals(getContents(), ((Tray) o).getContents()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  @Override public String toString() {
    return "Tray{" + "partType=" + partType + ", maxCapacity=" + maxCapacity + ", currentWeight=" + currentWeight + ", tray_id='" + tray_id + '\'' + ", contents=" + contents + '}';
  }
}
