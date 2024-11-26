package server.model.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity
@Table(name="tray")
public class Tray implements Serializable
{
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "tray_id_generator")
  @SequenceGenerator(name = "tray_id_generator", sequenceName = "tray_tray_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long trayId;


  @Column(name="maxweight_kilogram", nullable=false)
  private BigDecimal maxWeight_kilogram;


  @Column(name="weight_kilogram", nullable=false)
  private BigDecimal weight_kilogram;

  @OneToMany(mappedBy= "tray", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private List<AnimalPart> animalPartList = new ArrayList<>();


  @OneToMany(mappedBy="tray", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private List<TrayToProductTransfer> transferList = new ArrayList<>();

  @Transient
  private PartType trayType;

  /*@Transient
  private List<Long> animalPartIdList = new ArrayList<>();*/

  /*@Transient
  private List<Long> transferIdList = new ArrayList<>();*/

  @Transient
  private List<Product> productList = new ArrayList<>();

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected Tray() {
    //JPA requires this to be blank!
  }


  public Tray(long trayId, BigDecimal maxWeight_kilogram) {
    setTrayId(trayId);
    setMaxWeight_kilogram(maxWeight_kilogram);
    this.weight_kilogram = BigDecimal.ZERO;

    /*if(contentIdList != null && !contentIdList.isEmpty())
      this.animalPartIdList.addAll(contentIdList);

    if(transferIdList != null && !transferIdList.isEmpty())
      this.transferIdList.addAll(transferIdList);*/
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


  public long getTrayId() {
    return trayId;
  }


  public void setTrayId(long tray_id) {
    this.trayId = tray_id;
  }


  public List<AnimalPart> getAnimalPartList() {
    return Collections.unmodifiableList(animalPartList);
  }


  public void addAnimalPart(AnimalPart animalPart) throws DataIntegrityViolationException {
    if(animalPart != null) {
      // Check that there is room for more animalParts:
      BigDecimal availableCapacity = getMaxWeight_kilogram().subtract(getWeight_kilogram());
      System.out.println("Available capacity: " + availableCapacity);

      if(availableCapacity.subtract(animalPart.getWeight_kilogram()).compareTo(BigDecimal.valueOf(0)) < 0) {
        //There isn't space for this AnimalPart
        throw new DataIntegrityViolationException("Unable to add AnimalPart. Tray available Capacity (" + availableCapacity + "kg) is less than required (" + animalPart.getWeight_kilogram() + "kg)");
      }

      // Check that AnimalPart has the same PartType association, as this Tray:
      if (getTrayType() == null || getAnimalPartList().isEmpty()) {
        // This is the first AnimalPart being added. This part defines what AnimalParts this Tray may transport:
        setTrayType(animalPart.getType());
      } else {
        if(animalPart.getType().getTypeId() != getTrayType().getTypeId()) {
          throw new DataIntegrityViolationException("Unable to add AnimalPart. Trays may only contain 1 AnimalPart type. Tray already contains '" + getTrayType().getTypeDesc() + "', but attempting to add '" + animalPart.getType().getTypeDesc() + "'");
        }
      }

      // Add the AnimalPart:
      if(animalPartList != null) {
        animalPart.setTray(this);
        animalPartList.add(animalPart);
        setWeight_kilogram(getWeight_kilogram().add(animalPart.getWeight_kilogram()));
      } else {
        animalPartList = new ArrayList<>();
        animalPart.setTray(this);
        animalPartList.add(animalPart);
        setWeight_kilogram(getWeight_kilogram().add(animalPart.getWeight_kilogram()));
      }
    }
  }


  public void removeAnimalPart(AnimalPart animalPart) {
    if(animalPartList != null) {
      animalPart.setTray(null);
      animalPartList.remove(animalPart);
      setWeight_kilogram(getWeight_kilogram().subtract(animalPart.getWeight_kilogram()));
    }
  }


  public void clearAnimalPartList() {
    if(animalPartList != null) {
      animalPartList.clear();
      setWeight_kilogram(new BigDecimal(0));
    }
  }

  public void addAllAnimalParts(List<AnimalPart> animalParts) {
    if(animalParts != null) {
      for (AnimalPart animalPart : animalParts){
        if(!animalPartList.contains(animalPart)){
          animalPart.setTray(this);
          this.addAnimalPart(animalPart);
        }
      }
    }
  }

  @Transient
  public List<Long> getAnimalPartIdList() {
    return animalPartList.stream()
        .map(AnimalPart::getPartId)
        .collect(Collectors.toList());
  }

  @Transient
  public List<Long> getTransferIdList() {
    return transferList.stream()
        .map(TrayToProductTransfer::getTransferId)
        .collect(Collectors.toList());
  }

  public List<TrayToProductTransfer> getTransferList() {
    return Collections.unmodifiableList(transferList);
  }

  public boolean addTransfer(TrayToProductTransfer newTransfer) {
    if(newTransfer != null) {
      for (TrayToProductTransfer transfer : new ArrayList<>(transferList)) {
        if(newTransfer.getTransferId() == transfer.getTransferId()) {
          transferList.remove(transfer);
        }
      }
      transferList.add(newTransfer);
      return true;
    }
    return false;
  }

  public boolean removeTransfer(TrayToProductTransfer transferToDelete) {
    if(transferToDelete != null) {
      for (TrayToProductTransfer transfer : new ArrayList<>(transferList)) {
        if(transferToDelete.getTransferId() == transfer.getTransferId()) {
          transferList.remove(transfer);
          return true;
        }
      }
    }
    return false;
  }

  public List<Product> getProductList() {
    return Collections.unmodifiableList(productList);
  }

  public boolean addProduct(Product newProduct) {
    if(newProduct != null) {
      for (Product product : new ArrayList<>(productList)) {
        if(newProduct.getProductId() == product.getProductId()) {
          productList.remove(product);
          return true;
        }
      }
      productList.add(newProduct);
    }
    return false;
  }

  public boolean removeProduct(Product productToRemove) {
    if(productToRemove != null) {
      for (Product product : new ArrayList<>(productList)) {
        if(productToRemove.getProductId() == product.getProductId()) {
          productList.remove(product);
          return true;
        }
      }
    }
    return false;
  }

  public PartType getTrayType() {
    if(trayType == null && !getAnimalPartList().isEmpty()) {
      return getAnimalPartList().get(0).getType();
    }
    return trayType;
  }

  private void setTrayType(PartType trayType) {
    this.trayType = trayType;
  }

  // TODO: Update/Review equals, toString and hashcode methods
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Tray tray = (Tray) o;
    return getTrayId() == tray.getTrayId()
        && Objects.equals(getMaxWeight_kilogram(), tray.getMaxWeight_kilogram())
        && Objects.equals(getWeight_kilogram(), tray.getWeight_kilogram())
        //&& Objects.equals(getAnimalPartList(), tray.getAnimalPartList())
        //&& Objects.equals(getTransferList(), tray.getTransferList())
        && Objects.equals(getTrayType(), tray.getTrayType());
        //&& Objects.equals(getAnimalPartIdList(), tray.getAnimalPartIdList())
        //&& Objects.equals(getTransferIdList(), tray.getTransferIdList())
        //&& Objects.equals(getProductList(), tray.getProductList());
  }

  @Override public int hashCode() {
    return Objects.hash(getTrayId(),
        getMaxWeight_kilogram(),
        getWeight_kilogram(),
        //getAnimalPartList(),
        //getTransferList(),
        getTrayType());
        //getAnimalPartIdList(),
        //getTransferIdList(),
        //getProductList());
  }

  @Override public String toString() {
    String returnValue = "tray_id: '"
        + getTrayId()
        + ", maxCapacity: '"
        + getMaxWeight_kilogram()
        + "kg, currentWeight: '"
        + getWeight_kilogram()
        + "kg', only holds partType: '";

    returnValue += (getTrayType() != null) ? getTrayType().getTypeDesc() : "NULL";
    returnValue += "', product_id's parts were handed to: [";

    for (int i = 0; i < getTransferList().size(); i++) {
      returnValue += (getTransferList().get(i).getProduct() != null) ? getTransferList().get(i).getProduct().getProductId() : "NULL";
      if(i != getTransferList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "], animalPart_id's in Tray: [";

    for (int i = 0; i < getAnimalPartList().size(); i++) {
      returnValue += getAnimalPartList().get(i).getPartId();
      if(i != getAnimalPartList().size() - 1)
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


  public Tray copy() {
    Tray trayCopy = new Tray();
    trayCopy.setTrayId(getTrayId());
    trayCopy.setTrayType(getTrayType());
    trayCopy.setWeight_kilogram(getWeight_kilogram());
    trayCopy.setMaxWeight_kilogram(getMaxWeight_kilogram());

    for (AnimalPart animalPart : getAnimalPartList())
      trayCopy.addAnimalPart(animalPart);

    for (TrayToProductTransfer transfer : getTransferList())
      trayCopy.addTransfer(transfer);

    for (Product product : getProductList())
      trayCopy.addProduct(product);

    return trayCopy;
  }
}
