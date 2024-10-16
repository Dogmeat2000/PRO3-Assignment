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
@Table(name="PartType") // Tells spring boot JPA, what the name of this database table is.
public class PartType implements Serializable
{
  @Id                                                   // Tells Spring Boot, that this value is part of the primary key.
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "parttype_id_generator")  // Tells Spring Boot, that the primary key is generated by the database, using a sequence, and defines the specific generator in the database to utilize.
  @SequenceGenerator(name = "parttype_id_generator", sequenceName = "parttype_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long type_id;


  @Column(nullable=false) // Tells Spring Boot, that this is a column in the database, and that it cannot be null.
  private String type_Desc;


  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the AnimalPart entity,
  // and that the AnimalPart entity should 'own' the mapping. In other words, the type_id assigned in the 'AnimalPart' entity should be prioritized.
  // This makes logical sense, since it is from within the AnimalPart class that references to the PartType are stored in the DB!
  @OneToMany(mappedBy= "type")
  private List<AnimalPart> partList;


  // A no-args constructor, as required by tje Java Data API (JPA) specifications. Should not be used directly, thus protected!
  protected PartType() {
    //Note: Do not set the type_Id here, since JPA auto-sets this by using the database.
    setTypeDesc("NotSpecified");
    partList = new ArrayList<>();
  }


  public PartType(Long type_id, String type_Desc) {
    setTypeId(type_id);
    setTypeDesc(type_Desc);
  }


  public long getTypeId() {
    return type_id;
  }


  public void setTypeId(long id) {
    this.type_id = id;
  }


  public String getTypeDesc() {
    return type_Desc;
  }


  public void setTypeDesc(String desc) {
    this.type_Desc = desc;
  }


  public List<AnimalPart> getPartList() {
    return partList;
  }


  // TODO: Update/Review equals, toString and hashcode methods
  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    PartType partType = (PartType) o;
    return getTypeId() == partType.getTypeId() && Objects.equals(getTypeDesc(), partType.getTypeDesc()) && Objects.equals(getPartList(), partType.getPartList());
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(type_id, type_Desc, getPartList());
  }


  @Override public String toString() {
    return "PartType{" + "type_Id='" + type_id + '\'' + ", type_Desc='" + type_Desc + '\'' + '}';
  }
}
