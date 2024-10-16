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
@Table(name="Animal") // Tells spring boot JPA, what the name of this database table is.
public class Animal implements Serializable
{
  @Id                                                   // Tells Spring Boot, that this value is the primary key.
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "animal_id_seq_generator")  // Tells Spring Boot, that the primary key is generated by the database, using a sequence, and defines the specific generator in the database to utilize.
  @SequenceGenerator(name = "animal_id_seq_generator", sequenceName = "animal_animal_id_seq", allocationSize = 1) // Read documentation here: https://javabeat.net/jpa-annotations-generatedvalue-sequencegenerator-tablegenerator/
  private long animal_id;


  @Column (nullable=false) // Tells Spring Boot, that this is a column in the database, and that it cannot be null.
  private BigDecimal weight_kilogram;


  // @OneToMany Tells Spring Boot, that this database entity has a OneToMany relationship with the AnimalPart entity,
  // and that the AnimalPart entity should 'own' the mapping. In other words, the animal assigned in the 'AnimalPart' entity should be prioritized.
  // This makes logical sense, since it is from within the AnimalPart class that references to the Animal are stored in the DB!
  @OneToMany(mappedBy="animal", fetch = FetchType.EAGER)
  private List<AnimalPart> partList;


  // A no-args constructor, as required by the Java Data API (JPA) specifications.
  public Animal() {
    //Note: Do not set the animal_id here, since JPA auto-sets this by using the database.
    setWeight_kilogram(BigDecimal.valueOf(0));
    partList = new ArrayList<>();
  }


  public Animal(BigDecimal weight_kilogram) {
    setWeight_kilogram(weight_kilogram);
    partList = new ArrayList<>();
  }


  public Animal(long id, BigDecimal weight_kilogram) {
    setWeight_kilogram(weight_kilogram);
    setId(id);
    partList = new ArrayList<>();
  }


  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }


  public void setWeight_kilogram(BigDecimal weight) {
    this.weight_kilogram = weight;
  }


  public long getId() {
    return animal_id;
  }


  public void setId(long animal_id) {
    this.animal_id = animal_id;
  }


  public List<AnimalPart> getPartList()
  {
    return partList;
  }


  // TODO: Update/Review equals, toString and hashcode methods
  // Required by Spring Boot JPA:
  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getWeight_kilogram(), ((Animal) o).getWeight_kilogram())
        && Objects.equals(getId(), ((Animal) o).getId())
        && Objects.equals(getPartList(), ((Animal) o).getPartList()); //TODO Confirm that this equals method also performs equals check on contents.
  }


  // Required by Spring Boot JPA:
  @Override public int hashCode() {
    return Objects.hash(animal_id, getWeight_kilogram(), getPartList());
  }


  @Override public String toString() {
    return "Animal {\n" + "    animal_id = '" + animal_id + "',\n    weight = '" + weight_kilogram + "',\n    partList = " + partList + "\n}";
  }


  public Animal copy() {
    Animal animalCopy = new Animal();
    animalCopy.setId(getId());
    animalCopy.setWeight_kilogram(getWeight_kilogram());
    for (AnimalPart animalPart : getPartList()) {
      animalCopy.getPartList().add(animalPart);
    }
    return animalCopy;
  }
}
