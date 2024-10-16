package shared.model.entities;

import jakarta.persistence.*;
import server.repository.JPA_CompositeKeys.TrayToProductTransferId;

import java.util.Objects;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity // Assigns this class as an Entity for Spring Boot, to use as a base for its Data Persistance interface.
@IdClass(TrayToProductTransferId.class) // Tells Spring Boot JPA that this class uses a Composite Primary Key, and that the details for the key should follow 'TrayToProductTransferId' class in the JPA_CompositeKeys packaged.
@Table(name="TrayToProductTransfer") // Tells spring boot JPA, what the name of this database table is.
public class TrayToProductTransfer
{
  @Id // Tells Spring Boot, that this value is part of the primary key.
  @Column(name = "tray_id", nullable = false)
  private long tray_id; // Primitive type primary keys must be explicitly stated as defined in the @IdClass, when dealing with Entities with Composite Keys!


  @Id // Tells Spring Boot, that this value is part of the primary key.
  @Column(name = "product_id", nullable = false)
  private long product_id; // Primitive type primary keys must be explicitly stated as defined in the @IdClass, when dealing with Entities with Composite Keys!


  // @ManyToOne Tells Spring Boot, that this database entity has a ManyToOne relationship with the Tray entity,
  // and that the Tray entity should NOT 'own' the mapping. In other words, the type_id assigned inside this 'TrayToProductTransferRepository' entity should be prioritized.
  // This makes logical sense, since it is from within this Tray Entity that references to the Tray are stored as Foreign Keys, and not in the Tray entity!
  @ManyToOne
  @JoinColumn(name = "tray_id", referencedColumnName = "tray_id", nullable = false) // Tells Spring Boot, that the designated column in the Tray entity should be used when joining tables. Additionally, the name of the column is 'tray_id' in both tables.
  private Tray tray; // Object is automatically inserted into this variable by JPA when JPA interacts with DB, using the designated Composite Key.


  // @ManyToOne Tells Spring Boot, that this database entity has a ManyToOne relationship with the Tray entity,
  // and that the Tray entity should NOT 'own' the mapping. In other words, the type_id assigned inside this 'TrayToProductTransferRepository' entity should be prioritized.
  // This makes logical sense, since it is from within this Tray Entity that references to the Tray are stored as Foreign Keys, and not in the Tray entity!
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false) // Tells Spring Boot, that the designated column in the Tray entity should be used when joining tables. Additionally, the name of the column is 'tray_id' in both tables.
  private Product product; // Object is automatically inserted into this variable by JPA when JPA interacts with DB, using the designated Composite Key.


  // A no-args constructor, as required by tje Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected TrayToProductTransfer() {
  }


  public TrayToProductTransfer(Tray tray, Product product) {
    this.tray = tray;
    this.product = product;
  }


  public long getTray_id() {
    return tray_id;
  }


  public void setTray_id(long tray_id) {
    this.tray_id = tray_id;
  }


  public long getProduct_id() {
    return product_id;
  }


  public void setProduct_id(long product_id) {
    this.product_id = product_id;
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


  // TODO: Update/Review equals, toString and hashcode methods
  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TrayToProductTransfer that = (TrayToProductTransfer) o;
    return tray == that.tray && product == that.product;
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(tray, product);
  }
}
