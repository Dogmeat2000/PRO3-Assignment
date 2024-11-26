package server.model.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity
@Table(name="animalpart")
public class AnimalPart implements Serializable
{
  @Id
  @Column(name = "part_id")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "animal_part_id_generator")
  @SequenceGenerator(name = "animal_part_id_generator", sequenceName = "animalpart_part_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long partId;

  @Column(nullable=false) // Tells Spring Boot, that this is a column in the database, and that it cannot be null.
  private BigDecimal weight_kilogram;

  @ManyToOne (optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="animal_id", nullable=false) // Tells Spring Boot which DB column to use in the Animal entity when joining tables.
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private Animal animal;

  @ManyToOne (optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="type_id", nullable = false)  // Tells Spring Boot which DB column to use in the PartType entity when joining tables.
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private PartType type;

  @ManyToOne (optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="tray_id", nullable=false) // Tells Spring Boot which DB column to use in the Tray entity when joining tables.
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private Tray tray;

  @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="product_id")
  @JsonBackReference // Declares this class as child, to avoid infinite recursion.
  private Product product;

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected AnimalPart() {
    //JPA requires this to be blank!
  }

  public AnimalPart(long partId, BigDecimal weight_kilogram, PartType type, Animal animal, Tray tray, Product product) {
    setPartId(partId);
    setWeight_kilogram(weight_kilogram);
    setAnimal(animal);
    setTray(tray);
    setType(type);
    setTray(tray);
    setProduct(product);
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

  public Animal getAnimal() {
    return animal;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  public Tray getTray() {
    return tray;
  }

  public void setTray(Tray tray) {
    this.tray = tray;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public PartType getType() {
    return type;
  }

  public void setType(PartType partType) {
    this.type = partType;
  }

  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    AnimalPart that = (AnimalPart) o;
    return getPartId() == that.getPartId()
        && Objects.equals(getWeight_kilogram(), that.getWeight_kilogram())
        && Objects.equals(getAnimal(), that.getAnimal()) && Objects.equals(getType(), that.getType())
        && Objects.equals(getTray(), that.getTray())
        && Objects.equals(getProduct(), that.getProduct());
  }

  @Override public int hashCode() {
    return Objects.hash(getPartId(),
        getWeight_kilogram(),
        getAnimal(),
        getType(),
        getTray(),
        getProduct());
  }

  @Override public String toString() {
    return "animalPart_id: '"
        + getPartId()
        + "', weighing: '"
        + getWeight_kilogram()
        + "kg', cut from animal_id: '"
        + ((getAnimal() != null) ? getAnimal().getId() : "NULL")
        + "', with partType_id: '"
        + ((getType() != null) ? getType().getTypeId() : "NULL")
        + "', stored in tray_id: '"
        + ((getTray() != null) ? getTray().getTrayId() : "NULL")
        + "', packed into product_id: '"
        + ((getProduct() != null) ? getProduct().getProductId() : "NULL")
        + "'";
  }

  public AnimalPart copy() {
    AnimalPart animalPartCopy = new AnimalPart();
    animalPartCopy.setPartId(getPartId());
    animalPartCopy.setWeight_kilogram(getWeight_kilogram());
    animalPartCopy.setAnimal(getAnimal());
    animalPartCopy.setType(getType());
    animalPartCopy.setTray(getTray());
    animalPartCopy.setProduct(getProduct());

    return animalPartCopy;
  }
}
