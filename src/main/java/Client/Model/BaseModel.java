package Client.model;

public interface BaseModel {
  Object readEntityFromReceivedEntityList(long Id);
  void addEntityToReceivedEntityList(Object obj);
}
