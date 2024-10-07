package server.repository.JPA_CompositeKeys;

import java.io.Serializable;
import java.util.Objects;

// Defines the Composite Primary Key belonging to the Database Entity 'AnimalPart'.
public class AnimalPartId implements Serializable
{
  private long part_id;
  private long animal_id;
  private long type_id;
  private long tray_id;


  // No-args constructor required by Spring Boot JPA.
  protected AnimalPartId() {
  }


  public AnimalPartId(long part_id, long animal_id, long type_id, long tray_id) {
    this.part_id = part_id;
    this.animal_id = animal_id;
    this.type_id = type_id;
    this.tray_id = tray_id;
  }


  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AnimalPartId that = (AnimalPartId) o;
    return part_id == that.part_id && animal_id == that.animal_id && type_id == that.type_id && tray_id == that.tray_id;
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(part_id, animal_id, type_id, tray_id);
  }
}
