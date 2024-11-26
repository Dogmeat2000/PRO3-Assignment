package shared.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PartTypeDto
{
  private long typeId;
  private String type_Desc;
  private final List<Long> animalPartIdList = new ArrayList<>();

  public PartTypeDto(long typeId, String type_Desc) {
    setTypeId(typeId);
    setTypeDesc(type_Desc);
  }

  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long id) {
    this.typeId = id;
  }

  public String getTypeDesc() {
    return type_Desc;
  }

  public void setTypeDesc(String desc) {
    this.type_Desc = desc;
  }

  public List<Long> getAnimalPartIdList() {
    return Collections.unmodifiableList(animalPartIdList);
  }

  public void addAnimalPartId(long animalPartId) {
    if(animalPartId != 0 && !animalPartIdList.contains(animalPartId))
      animalPartIdList.add(animalPartId);
  }

  public void removeAnimalPartId(long animalPartId) {
    if(animalPartId != 0)
      animalPartIdList.remove(animalPartId);
  }

  public void clearAnimalPartIdList() {
    animalPartIdList.clear();
  }

  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    PartTypeDto that = (PartTypeDto) o;
    return getTypeId() == that.getTypeId() && Objects.equals(type_Desc, that.type_Desc) && Objects.equals(getAnimalPartIdList(), that.getAnimalPartIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getTypeId(), type_Desc, getAnimalPartIdList());
  }

  @Override public String toString() {
    String returnValue = "partType_id: '"
        + getTypeId()
        + "', desc: '"
        + getTypeDesc()
        +  "', animalPart_ids of this Type: [";

    for (int i = 0; i < getAnimalPartIdList().size(); i++) {
      returnValue += getAnimalPartIdList().get(i);
      if(i != getAnimalPartIdList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "]";

    return returnValue;
  }

  public PartTypeDto copy() {
    PartTypeDto partTypeCopy = new PartTypeDto(getTypeId(), getTypeDesc());
    for (long id : getAnimalPartIdList()) {
      partTypeCopy.addAnimalPartId(id);
    }
    return partTypeCopy;
  }
}
