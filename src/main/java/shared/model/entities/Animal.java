package shared.model.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal
{
  private BigDecimal weight;
  private long animal_id;
  private List<AnimalPart> partList;


  public Animal() {
    setWeight(BigDecimal.valueOf(0));
    setId(-1);
    partList = new ArrayList<>();
  }

  public Animal(BigDecimal weight) {
    setWeight(weight);
    setId(-1);
    partList = new ArrayList<>();
  }

  public Animal(long id, BigDecimal weight) {
    setWeight(weight);
    setId(id);
    partList = new ArrayList<>();
  }


  public BigDecimal getWeight() {
    return weight;
  }


  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }


  public long getId() {
    return animal_id;
  }


  public void setId(long animal_id) {
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
        && Objects.equals(getId(), ((Animal) o).getId())
        && Objects.equals(getPartList(), ((Animal) o).getPartList()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  @Override public String toString() {
    return "Animal{" + "weight=" + weight + ", animal_id='" + animal_id + '\'' + ", partList=" + partList + '}';
  }

  public Animal copy() {
    Animal animalCopy = new Animal();
    animalCopy.setId(getId());
    animalCopy.setWeight(getWeight());
    for (AnimalPart animalPart : getPartList()) {
      animalCopy.getPartList().add(animalPart);
    }
    return animalCopy;
  }
}
