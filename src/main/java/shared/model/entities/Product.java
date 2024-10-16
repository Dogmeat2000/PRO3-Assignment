package shared.model.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity // Assigns this class as an Entity for Spring Boot, to use as a base for its Data Persistance interface.
@Table(name="product") // Tells spring boot JPA, what the name of this database table is.
public class Product implements Serializable
{
  @Id                                                   // Tells Spring Boot, that this value is part of the primary key.
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "product_id_generator")  // Tells Spring Boot, that the primary key is generated by the database, using a sequence, and defines the specific generator in the database to utilize.
  @SequenceGenerator(name = "product_id_generator", sequenceName = "product_product_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long product_id;


  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the AnimalPart entity,
  // and that the AnimalPart entity should 'own' the mapping (using the product attribute in its class). In other words, the product_id assigned in the 'AnimalPart' entity should be prioritized.
  // This makes logical sense, since it is from within the AnimalPart class that references to the Product are stored in the DB between AnimalPart and Product.
  @OneToMany(mappedBy="product", cascade = CascadeType.ALL) //Cascade ensures that JPA applies changes in AnimalPart to all linked classes (incl. this class)
  private List<AnimalPart> contentList;


  // Special Note: JPA works with Database entities as objects, so the join table is here modelled as its own object. This allows for future scalability,
  // where I can decide to add timestamp, etc. to the join table without much change to this code!
  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the TrayToProductTransferRepository entity,
  // also that TrayToProductTransferRepository entity should 'own' the mapping (using the product attribute in its class).
  // This makes logical sense, since it is from within the TrayToProductTransferRepository class that references to the Product are stored in the DB, between Product and Tray.
  @OneToMany(mappedBy="product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrayToProductTransfer> traySupplyJoinList;

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected Product() {
    //Note: Do not set the product_id here, since JPA auto-sets this by using the database.
    contentList = new ArrayList<>();
    traySupplyJoinList = new ArrayList<>();
  }


  public Product(long id, List<AnimalPart> contentList, List<TrayToProductTransfer> traySupplyJoinList) {
    setProduct_id(id);

    this.contentList = new ArrayList<>();
    getContentList().addAll(contentList);

    this.traySupplyJoinList = new ArrayList<>();
    getTraySupplyJoinList().addAll(traySupplyJoinList);
  }


  public long getProduct_id() {
    return product_id;
  }


  public void setProduct_id(long product_id) {
    this.product_id = product_id;
  }


  public List<AnimalPart> getContentList() {
    return contentList;
  }


  public List<TrayToProductTransfer> getTraySupplyJoinList() {
    return traySupplyJoinList;
  }


  // TODO: Update/Review equals, toString and hashcode methods
  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getProduct_id(), ((Product) o).getProduct_id())
        && Objects.equals(getContentList(), ((Product) o).getContentList()) //TODO Confirm that this equals method also performs equals check on contents.
        && Objects.equals(getTraySupplyJoinList(), ((Product) o).getTraySupplyJoinList()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(getProduct_id(), getContentList(), getTraySupplyJoinList());
  }


  // Required by Spring Boot JPA:
  @Override public String toString() {
    return "Product{" + "product_id='" + product_id + '\'' + ", contentList=" + contentList + ", traySupplyList=" + traySupplyJoinList + '}';
  }
}
