package server.repository.JPA_CompositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

// Defines the Composite Primary Key belonging to the Database Entity 'TrayToProductTransferRepository'.
@Embeddable
public class TrayToProductTransferId implements Serializable
{
  //@Column(name = "tray_id")
  private long tray_id;

  //@Column(name = "product_id")
  private long product_id;

  // No-args constructor required by Spring Boot JPA.
  protected TrayToProductTransferId() {
  }

  public TrayToProductTransferId(long tray_id, long product_id) {
    this.tray_id = tray_id;
    this.product_id = product_id;
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

  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TrayToProductTransferId that = (TrayToProductTransferId) o;
    return tray_id == that.tray_id && product_id == that.product_id;
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(tray_id, product_id);
  }
}
