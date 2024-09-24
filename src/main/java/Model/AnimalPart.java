package Model;

import java.math.BigDecimal;
import java.util.Objects;

public class AnimalPart extends PartType
{
  private BigDecimal weight;
  private String part_Id;
  private Animal animal;


  public AnimalPart(Animal animal) {
    super();
    setWeight(BigDecimal.valueOf(0));
    setPart_Id("NotSpecified");
    setAnimal(animal);
  }


  public BigDecimal getWeight() {
    return weight;
  }


  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }


  public String getPart_Id() {
    return part_Id;
  }


  public void setPart_Id(String part_Id) {
    this.part_Id = part_Id;
  }


  public Animal getAnimal() {
    return animal;
  }


  private void setAnimal(Animal animal) {
    this.animal = animal;
  }

  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getWeight(), ((AnimalPart) o).getWeight())
        && Objects.equals(getPart_Id(), ((AnimalPart) o).getPart_Id())
        && Objects.equals(getAnimal(), ((AnimalPart) o).getAnimal())
        && super.equals((PartType) o);
  }


  @Override public String toString() {
    return "AnimalPart{" + "weight=" + weight + ", part_Id='" + part_Id + '\'' + ", animal=" + animal + '}';
  }
}
