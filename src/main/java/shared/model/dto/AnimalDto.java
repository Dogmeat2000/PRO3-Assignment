package shared.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AnimalDto implements Serializable
{
  @JsonProperty("animalId")
  private long animalId;

  @JsonProperty("weight_kilogram")
  private BigDecimal weight_kilogram;

  @JsonProperty("origin")
  private String origin;

  @JsonProperty("arrival_date")
  private Date arrivalDate;

  @JsonProperty("animalPartIdList")
  private List<Long> animalPartIdList = new ArrayList<>();

  // No-Args constructor for Jackson, JSON parser:
  public AnimalDto() {}

  public AnimalDto(long animalId, BigDecimal weight_kilogram, String origin, Date arrivalDate, List<Long> animalPartIdList) {
    setAnimalId(animalId);
    setWeight_kilogram(weight_kilogram);
    setOrigin(origin);
    setArrivalDate(arrivalDate);
    setAnimalPartIdList(animalPartIdList);
  }

  public long getAnimalId() {
    return animalId;
  }

  public void setAnimalId(long animalId) {
    this.animalId = animalId;
  }

  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }

  public void setWeight_kilogram(BigDecimal weight_kilogram) {
    this.weight_kilogram = weight_kilogram;
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

  public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public List<Long> getAnimalPartIdList() {
    return animalPartIdList;
  }

  public void setAnimalPartIdList(List<Long> animalPartIdList) {
    this.animalPartIdList = animalPartIdList;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AnimalDto animalDto = (AnimalDto) o;
    return getAnimalId() == animalDto.getAnimalId() && Objects.equals(getWeight_kilogram(), animalDto.getWeight_kilogram()) && Objects.equals(getOrigin(), animalDto.getOrigin()) && Objects.equals(
        getArrivalDate(), animalDto.getArrivalDate()) && Objects.equals(getAnimalPartIdList(), animalDto.getAnimalPartIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getAnimalId(), getWeight_kilogram(), getOrigin(), getArrivalDate(), getAnimalPartIdList());
  }

  @Override public String toString() {
    return "AnimalDto{" + "animalId=" + animalId + ", weight_kilogram=" + weight_kilogram + ", origin='" + origin + '\'' + ", arrival_date=" + arrivalDate + ", animalPartIdList=" + animalPartIdList
        + '}';
  }

  public AnimalDto copy(){
    return new AnimalDto(getAnimalId(),
        getWeight_kilogram(),
        getOrigin(),
        getArrivalDate(),
        getAnimalPartIdList());
  }
}
