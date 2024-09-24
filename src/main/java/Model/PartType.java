package Model;

import java.util.Objects;

public class PartType
{
  private String type_Id, type_Desc;


  public PartType() {
    setTypeId("NotSpecified");
    setTypeDesc("NotSpecified");
  }


  public String getTypeId() {
    return type_Id;
  }


  public void setTypeId(String id) {
    this.type_Id = id;
  }


  public String getTypeDesc() {
    return type_Desc;
  }


  public void setTypeDesc(String desc) {
    this.type_Desc = desc;
  }


  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getTypeId(), ((PartType) o).getTypeId())
        && Objects.equals(getTypeDesc(), ((PartType) o).getTypeDesc());
  }


  @Override public String toString() {
    return "PartType{" + "type_Id='" + type_Id + '\'' + ", type_Desc='" + type_Desc + '\'' + '}';
  }
}
