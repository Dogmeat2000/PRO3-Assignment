package server.model.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity
@Table(name="product")
public class Product implements Serializable
{
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
  @SequenceGenerator(name = "product_id_generator", sequenceName = "product_product_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long productId;

  @OneToMany(mappedBy="product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private List<AnimalPart> animalPartList = new ArrayList<>();

  @OneToMany(mappedBy="product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private List<TrayToProductTransfer> traySupplyJoinList = new ArrayList<>();;

  @Transient
  private List<Tray> traySuppliersList = new ArrayList<>();

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected Product() {
    //JPA requires this to be blank!
  }

  public Product(long id, List<AnimalPart> animalPartList, List<Tray> traySuppliersList) {
    setProductId(id);
    addAllAnimalParts(animalPartList);
    addAllTraysToTraySuppliersList(traySuppliersList);
  }

  public long getProductId() {
    return productId;
  }


  public void setProductId(long product_id) {
    this.productId = product_id;
  }

  public List<AnimalPart> getAnimalPartList() {
    return Collections.unmodifiableList(animalPartList);
  }

  public void addAllAnimalParts(List<AnimalPart> animalParts) {
    if(animalParts != null) {
      for (AnimalPart animalPart : animalParts){
        if(animalPart != null && !animalPartList.contains(animalPart)){
          animalPart.setProduct(this);
          this.addAnimalPart(animalPart);
        }
      }
    }
  }

  public void addAnimalPart(AnimalPart animalPart) {
    if(animalPart != null) {
      animalPart.setProduct(this);
      animalPartList.add(animalPart);
    }
  }

  public boolean removeAnimalPart(AnimalPart animalPartToRemove) {
    if(animalPartToRemove != null) {
      for (AnimalPart animalPart : new ArrayList<>(getAnimalPartList())){
        if(animalPart.getPartId() == animalPartToRemove.getPartId()){
          animalPartList.remove(animalPart);
          return true;
        }
      }
    }
    return false;
  }

  public void clearAnimalPartList() {
    for (AnimalPart animalPart : new ArrayList<>(animalPartList)) {
      removeAnimalPart(animalPart);
    }
  }

  public List<Long> getAnimalPartIdList() {
    return animalPartList.stream()
        .map(AnimalPart::getPartId)
        .collect(Collectors.toList());
  }

  public List<TrayToProductTransfer> getTraySupplyJoinList() {
    return Collections.unmodifiableList(traySupplyJoinList);
  }

  public void addTransfer(TrayToProductTransfer transfer) {
    if(transfer != null) {
      transfer.setProduct(this);
      traySupplyJoinList.add(transfer);
    }
  }

  public void addAllTransfersToTraySupplyJoinList(List<TrayToProductTransfer> transferList) {
    if(transferList != null) {
      for (TrayToProductTransfer transfer : transferList){
        if(!traySupplyJoinList.contains(transfer)){
          this.addTransfer(transfer);
        }
      }
    }
  }

  public void clearTraySupplyJoinList() {
    for (TrayToProductTransfer transfer : new ArrayList<>(traySupplyJoinList))
      traySupplyJoinList.remove(transfer);
  }

  public List<Long> getTransferIdList() {
    return traySupplyJoinList.stream()
        .map(TrayToProductTransfer::getTransferId)
        .collect(Collectors.toList());
  }

  public List<Tray> getTraySuppliersList() {
    return Collections.unmodifiableList(traySuppliersList);
  }

  public void clearTraySuppliersList() {
    for (Tray tray : new ArrayList<>(traySuppliersList))
      traySuppliersList.remove(tray);
  }

  public void addTrayToTraySuppliersList(Tray tray) {
    if(tray != null) {
      traySuppliersList.add(tray);
    }
  }

  public void addAllTraysToTraySuppliersList(List<Tray> traySuppliersList) {
    if(traySuppliersList != null) {
      for (Tray tray : traySuppliersList){
        if(!traySuppliersList.contains(tray)){
          this.addTrayToTraySuppliersList(tray);
        }
      }
      this.traySuppliersList = traySuppliersList;
    }
  }


  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    Product product = (Product) o;
    return getProductId() == product.getProductId()
        && Objects.equals(getAnimalPartIdList(), product.getAnimalPartIdList())
        && Objects.equals(getTransferIdList(), product.getTransferIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getProductId(),
        getAnimalPartList(),
        getTransferIdList(),
        getAnimalPartIdList());
  }

  // Required by Spring Boot JPA:
  @Override public String toString() {
    String returnValue = "product_id: '"
        + getProductId()
        + "', List of associated trayId's: [";

    for (int i = 0; i < getTraySuppliersList().size(); i++) {
      returnValue += ((getTraySuppliersList().get(i) != null) ? getTraySuppliersList().get(i).getTrayId() : "NULL");
      if(i < getTraySuppliersList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "], List of animalPart_id's in this product: [";

    for (int i = 0; i < getAnimalPartList().size(); i++) {
      returnValue += getAnimalPartList().get(i).getPartId();
      if(i < getAnimalPartList().size() - 1)
        returnValue += ", ";
    }

    returnValue += "]";

    return returnValue;
  }

  public Product copy() {
    Product copy = new Product(getProductId(),
        new ArrayList<>(getAnimalPartList()), new ArrayList<>(getTraySuppliersList()));
    copy.addAllTraysToTraySuppliersList(getTraySuppliersList());

    return copy;
  }
}
