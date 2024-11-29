package DataServer.model.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity
@Table(name="traytoproducttransfer")
public class TrayToProductTransfer
{
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "transfer_id_seq_generator")
  @SequenceGenerator(name = "transfer_id_seq_generator", sequenceName = "traytoproducttransfer_transfer_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long transferId;

  @ManyToOne (fetch = FetchType.EAGER)
  @JoinColumn(name = "tray_id", nullable = false)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private Tray tray;

  @ManyToOne (fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id", nullable = false)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private Product product;

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected TrayToProductTransfer() {
    //JPA requires this to be blank!
  }

  public TrayToProductTransfer(long transferId, Tray tray, Product product) {
    setTransferId(transferId);
    setTray(tray);
    setProduct(product);
  }

  public long getTransferId() {
    return transferId;
  }

  public void setTransferId(long id) {
    this.transferId = id;
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

  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    TrayToProductTransfer that = (TrayToProductTransfer) o;
    return getTransferId() == that.getTransferId()
        && Objects.equals(getTray(), that.getTray())
        && Objects.equals(getProduct(), that.getProduct());
  }

  @Override public int hashCode() {
    return Objects.hash(getTransferId(),
        getTray(),
        getProduct());
  }
}
