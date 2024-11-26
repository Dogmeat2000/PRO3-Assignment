package server.model.persistence.entities;

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
@Table(name="parttype")
public class PartType implements Serializable
{
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "parttype_id_generator")
  @SequenceGenerator(name = "parttype_id_generator", sequenceName = "parttype_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  @Column(name="id")
  private long typeId;

  @Column(name="desc", nullable=false)
  private String type_Desc;

  @OneToMany(mappedBy= "type", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private List<AnimalPart> animalPartList = new ArrayList<>();

  // A no-args constructor, as required by the Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected PartType() {
    //JPA requires this to be blank!
  }

  public PartType(long typeId, String type_Desc) {
    setTypeId(typeId);
    setTypeDesc(type_Desc);
  }

  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long id) {
    this.typeId = id;
  }

  public String getTypeDesc() {
    return type_Desc;
  }

  public void setTypeDesc(String desc) {
    this.type_Desc = desc;
  }

  public List<AnimalPart> getAnimalPartList() {
    return Collections.unmodifiableList(animalPartList);
  }

  public void setAnimalParts(List<AnimalPart> animalParts) {
    if(animalParts != null) {
      this.animalPartList = animalParts;
      // Ensure that all assigned AnimalParts have this PartType as the parent:
      for (AnimalPart animalPart : animalParts) {
        animalPart.setType(this);
      }
    }
  }

  public void addAnimalPart(AnimalPart animalPart) {
    if(animalPart != null) {
      // Set parent PartType to this PartType when adding:
      animalPart.setType(this);
      this.animalPartList.add(animalPart);
    }
  }


  public void removeAnimalPart(AnimalPart animalPart) {
    if(animalPart != null) {
      // Set parent PartType to NULL when removing.
      this.animalPartList.remove(animalPart);
      animalPart.setType(null);
    }
  }


  @Transient
  public List<Long> getAnimalPartIdList() {
    return animalPartList.stream()
        .map(AnimalPart::getPartId)
        .collect(Collectors.toList());
  }

  public void clearAnimalPartList() {
    for (AnimalPart animalPart : new ArrayList<>(animalPartList)) {
      removeAnimalPart(animalPart);
    }
  }


  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    PartType partType = (PartType) o;
    return getTypeId() == partType.getTypeId()
        && Objects.equals(type_Desc, partType.type_Desc)
        && Objects.equals(getAnimalPartIdList(), partType.getAnimalPartIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getTypeId(),
        type_Desc,
        getAnimalPartIdList());
  }

  @Override public String toString() {
    String returnValue = "partType_id: '"
        + getTypeId()
        + "', desc: '"
        + getTypeDesc()
        +  "', animalPart_ids of this Type: [";

    for (int i = 0; i < getAnimalPartList().size(); i++) {
      returnValue += getAnimalPartList().get(i).getPartId();
      if(i != getAnimalPartList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "]";

    return returnValue;
  }

  public PartType copy() {
    PartType partTypeCopy = new PartType(getTypeId(), getTypeDesc());
    for (AnimalPart animalPart : getAnimalPartList()) {
      partTypeCopy.addAnimalPart(animalPart.copy());
    }
    return partTypeCopy;
  }
}
