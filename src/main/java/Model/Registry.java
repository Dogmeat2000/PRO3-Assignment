package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Registry
{
  private List<Animal> animalList;
  private List<AnimalPart> animalPartList;
  private List<Product> productList;


  public Registry() {
    animalList = new ArrayList<>();
    animalPartList = new ArrayList<>();
    productList = new ArrayList<>();
  }


  public List<Animal> getAnimalList() {
    return animalList;
  }


  public List<AnimalPart> getAnimalPartList() {
    return animalPartList;
  }


  public List<Product> getProductList() {
    return productList;
  }


  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getAnimalList(), ((Registry) o).getAnimalList()) //TODO Confirm that this equals method also performs equals check on contents.
        && Objects.equals(getProductList(), ((Registry) o).getProductList()) //TODO Confirm that this equals method also performs equals check on contents.
        && Objects.equals(getAnimalPartList(), ((Registry) o).getAnimalPartList()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  @Override public String toString() {
    return "Registry{" + "animalList=" + animalList + ", animalPartList=" + animalPartList + ", productList=" + productList + '}';
  }
}
