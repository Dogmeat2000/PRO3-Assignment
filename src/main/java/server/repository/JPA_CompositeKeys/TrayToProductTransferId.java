package server.repository.JPA_CompositeKeys;

import java.io.Serializable;
import java.util.Objects;

// Defines the Composite Primary Key belonging to the Database Entity 'TrayToProductTransfer'.
public class TrayToProductTransferId implements Serializable
{
  private long tray_id;
  private long product_id;

  // No-args constructor required by Spring Boot JPA.
  protected TrayToProductTransferId() {
  }

  public TrayToProductTransferId(long tray_id, long product_id) {
    this.tray_id = tray_id;
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
