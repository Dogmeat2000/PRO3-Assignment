package Client.common.model;

import java.util.List;

public interface QueueManager extends Runnable
{
  boolean addToUnregisteredQueue(Object obj) throws IllegalArgumentException;
  List<Object> copyRegisteredQueue();
}
