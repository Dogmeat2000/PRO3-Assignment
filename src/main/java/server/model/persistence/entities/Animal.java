package server.model.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

// Find the Spring Boot database documentation here: (https://docs.spring.io/spring-boot/reference/data/sql.html)
// Good guide on JPA here: https://www.infoworld.com/article/2259742/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
// Find a good manual on how to use Spring Boot with JPA Database management here: https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d
@Entity
@Table(name="Animal")
public class Animal implements Serializable
{
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "animal_id_seq_generator")
  @SequenceGenerator(name = "animal_id_seq_generator", sequenceName = "animal_animal_id_seq", allocationSize = 1)
  private long animalId;

  @Column (nullable=false)
  private BigDecimal weight_kilogram;

  @Column
  private String origin;

  @Column
  private Date arrivalDate;

  @OneToMany(mappedBy="animal", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference // Declares this class as parent, to avoid infinite recursion.
  private List<AnimalPart> animalPartList = new ArrayList<>();


  //@Transient
  //private List<Long> animalPartIdList = new ArrayList<>();


  // A no-args constructor, as required by the Java Data API (JPA) specifications.
  public Animal() {
    //JPA requires this to be blank!
  }


  public Animal(BigDecimal weight_kilogram) {
    setWeight_kilogram(weight_kilogram);
  }


  public Animal(long id, BigDecimal weight_kilogram, String origin, Date arrivalDate) {
    setWeight_kilogram(weight_kilogram);
    setId(id);
    setOrigin(origin);
    setArrivalDate(arrivalDate);
  }

  public long getId() {
    return animalId;
  }

  public void setId(long animal_id) {
    this.animalId = animal_id;
  }


  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }


  public void setWeight_kilogram(BigDecimal weight) {
    this.weight_kilogram = weight;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public Date getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(Date arrival_date) {
    this.arrivalDate = arrival_date;
  }

  public List<AnimalPart> getAnimalPartList() {
    return Collections.unmodifiableList(animalPartList);
  }


  public void setAnimalParts(List<AnimalPart> animalParts) {
    if(animalParts != null) {
      this.animalPartList = animalParts;
      // Ensure that all assigned AnimalParts have this Animal as the parent:
      for (AnimalPart animalPart : animalParts) {
        animalPart.setAnimal(this);
      }
    }
  }


  public void addAnimalPart(AnimalPart animalPart) {
    // Set parent Animal to this Animal when adding:
    if(animalPart != null) {
      animalPart.setAnimal(this);
      this.animalPartList.add(animalPart);
    }
  }


  public void removeAnimalPart(AnimalPart animalPart) {
    if(animalPart != null) {
      // Set parent Animal to NULL when removing.
      this.animalPartList.remove(animalPart);
      animalPart.setAnimal(null);
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




  /*public void setAnimalPartIdList(List<Long> animalPartIds) {
    this.animalPartIdList = animalPartIds;
  }*/


  // TODO: Update/Review equals, toString and hashcode methods
  // Required by Spring Boot JPA:

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Animal animal = (Animal) o;
    return animalId == animal.animalId
        && Objects.equals(getWeight_kilogram(), animal.getWeight_kilogram())
        && Objects.equals(getOrigin(), animal.getOrigin())
        && Timestamp.from(getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)).equals(Timestamp.from(animal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
        //&& Objects.equals(getPartList(), animal.getPartList())
        //&& Objects.equals(getAnimalPartIdList(), animal.getAnimalPartIdList())
  }

  @Override public int hashCode() {
    return Objects.hash(animalId,
        getWeight_kilogram(),
        getOrigin(),
        Timestamp.from(getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
        //getPartList(),
        //getAnimalPartIdList()
  }

  @Override public String toString() {
    String returnValue = "animal_id: '"
        + getId()
        + "', weight: '"
        + getWeight_kilogram()
        + "kg', origin: '"
        + getOrigin()
        + "', arrival_date: '"
        + getArrivalDate()
        +  "', animalPart_ids cut from this animal: [";

    for (int i = 0; i < getAnimalPartList().size(); i++) {
      returnValue += getAnimalPartList().get(i).getPartId();
      if(i != getAnimalPartList().size() - 1)
        returnValue += ", ";
    }
    returnValue += "]";
        
    return returnValue;
  }


  public Animal copy() {
    Animal animalCopy = new Animal(getWeight_kilogram());
    animalCopy.setId(getId());
    animalCopy.setOrigin(getOrigin());
    animalCopy.setArrivalDate(getArrivalDate());
    for (AnimalPart animalPart : getAnimalPartList()) {
      animalCopy.addAnimalPart(animalPart.copy());
    }
    return animalCopy;
  }
}
