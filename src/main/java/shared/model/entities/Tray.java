package shared.model.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity // Assigns this class as an Entity for Spring Boot, to use as a base for its Data Persistance interface.
@Table(name="Tray") // Tells spring boot JPA, what the name of this database table is.
public class Tray implements Serializable
{
  @Id                                                   // Tells Spring Boot, that this value is the primary key.
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "tray_id_generator")  // Tells Spring Boot, that the primary key is generated by the database, using a sequence, and defines the specific generator in the database to utilize.
  @SequenceGenerator(name = "tray_id_generator", sequenceName = "tray_tray_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long tray_id;


  @Column(nullable=false) // Tells Spring Boot, that this is a column in the database, and that it cannot be null.
  private BigDecimal maxWeight_kilogram;


  @Column(nullable=false) // Tells Spring Boot, that this is a column in the database, and that it cannot be null.
  private BigDecimal weight_kilogram;


  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the AnimalPart entity,
  // and that the AnimalPart entity should 'own' the mapping. In other words, the tray_id assigned in the 'AnimalPart' entity should be prioritized.
  // This makes logical sense, since it is from within the AnimalPart class that references to the Tray are stored!
  @OneToMany(mappedBy= "tray", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AnimalPart> contents;


  // Special Note: JPA works with Database entities as objects, so the join table is here modelled as its own object. This allows for future scalability,
  // where I can decide to add timestamp, etc. to the join table without much change to this code!
  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the TrayToProductTransferRepository entity,
  // also that TrayToProductTransferRepository entity should 'own' the mapping (using the tray attribute in its class).
  // This makes logical sense, since it is from within the TrayToProductTransferRepository class that references to the Tray are stored in the DB, between Product and Tray.
  @OneToMany(mappedBy="tray")
  private List<TrayToProductTransfer> deliveredToProducts;


  // A no-args constructor, as required by tje Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected Tray() {
    //Note: Do not set the tray_id here, since JPA auto-sets this by using the database.
    setWeight_kilogram(BigDecimal.ZERO);
    setMaxWeight_kilogram(BigDecimal.ZERO);
    this.contents = new ArrayList<>();
    this.deliveredToProducts = new ArrayList<>();
  }


  public Tray(Long tray_id, BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram) {
    setTray_id(tray_id);
    setMaxWeight_kilogram(maxWeight_kilogram);
    setWeight_kilogram(weight_kilogram);
    this.contents = new ArrayList<>();
    this.deliveredToProducts = new ArrayList<>();
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


  public void setWeight_kilogram(BigDecimal currentWeight) {
    this.weight_kilogram = currentWeight;
  }


  public long getTray_id() {
    return tray_id;
  }


  public void setTray_id(long tray_id) {
    this.tray_id = tray_id;
  }


  public List<AnimalPart> getContents() {
    return contents;
  }


  public List<TrayToProductTransfer> getDeliveredToProducts() {
    return deliveredToProducts;
  }


  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getTray_id(), ((Tray) o).getTray_id())
        && Objects.equals(getWeight_kilogram(), ((Tray) o).getWeight_kilogram())
        && Objects.equals(getMaxWeight_kilogram(), ((Tray) o).getMaxWeight_kilogram())
        && Objects.equals(getContents(), ((Tray) o).getContents()) //TODO Confirm that this equals method also performs equals check on contents.
        && Objects.equals(getDeliveredToProducts(), ((Tray) o).getDeliveredToProducts()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(getTray_id(), getMaxWeight_kilogram(), getWeight_kilogram(), getContents());
  }

  @Override public String toString() {
    return "Tray{" + ", maxCapacity=" + maxWeight_kilogram + ", currentWeight=" + weight_kilogram + ", tray_id='" + tray_id + '\'' + ", contents=" + contents + '}';
  }
}
