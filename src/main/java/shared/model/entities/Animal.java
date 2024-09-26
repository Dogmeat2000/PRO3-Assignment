package shared.model.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal
{
  private BigDecimal weight;
  private String animal_id;
  private List<AnimalPart> partList;


  public Animal() {
    setWeight(BigDecimal.valueOf(0));
    setAnimal_id("NotSpecified");
    partList = new ArrayList<>();
  }


  public BigDecimal getWeight() {
    return weight;
  }


  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }


  public String getAnimal_id() {
    return animal_id;
  }


  public void setAnimal_id(String animal_id) {
    this.animal_id = animal_id;
  }


  public List<AnimalPart> getPartList()
  {
    return partList;
  }


  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getWeight(), ((Animal) o).getWeight())
        && Objects.equals(getAnimal_id(), ((Animal) o).getAnimal_id())
        && Objects.equals(getPartList(), ((Animal) o).getPartList()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  @Override public String toString() {
    return "Animal{" + "weight=" + weight + ", animal_id='" + animal_id + '\'' + ", partList=" + partList + '}';
  }
}
