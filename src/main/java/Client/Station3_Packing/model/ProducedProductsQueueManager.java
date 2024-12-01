package Client.Station3_Packing.model;

import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystem;
import Client.common.model.QueueManager;
import shared.model.dto.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducedProductsQueueManager implements QueueManager
{
  private final Queue<ProductDto> unregisteredProductQueue = new LinkedList<>();
  private final ProductRegistrationSystem productRegistrationSystem;

  public ProducedProductsQueueManager(ProductRegistrationSystem productRegistrationSystem){
    this.productRegistrationSystem = productRegistrationSystem;
  }

  @Override public void run() {
    // Handle the unregistered Products on its own thread:
    Thread handleUnregisteredProductsThread = new Thread(this::handleUnregisteredProducts);
    handleUnregisteredProductsThread.setDaemon(true);
    handleUnregisteredProductsThread.start();

    // Wait for the thread to close:
    try {
      handleUnregisteredProductsThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleUnregisteredProducts(){
    // Run continuously for as long as this thread lives:
    while(true){
      // Check if Queue is non-empty:
      if(!unregisteredProductQueue.isEmpty()){
        // Extract data:
        ProductDto product = unregisteredProductQueue.poll();
        try {
          List<AnimalPartDto> animalPartList = new ArrayList<>();
          for (Long id : product.getAnimalPartIdList()){
            AnimalPartDto animalPart = new AnimalPartDto();
            animalPart.setPartId(id);
            animalPartList.add(animalPart);
          }

          List<TrayDto> trayList = new ArrayList<>();
          for (Long id : product.getTrayIdList()){
            TrayDto tray = new TrayDto();
            tray.setTrayId(id);
            trayList.add(tray);
          }

          // Attempt to register, using gRPC connection:
          ProductDto registeredProduct = productRegistrationSystem.registerNewProduct(animalPartList, trayList);
          System.out.println("\n[QueueManager] Saved Product {" + registeredProduct + "} to database.");

        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("\n[QueueManager] Failed to register Product {" + product + "}. Reason: " + e.getMessage());
          System.err.println("[QueueManager] Adding failed Product to the end of the queue, and trying next Product in queue.");
          unregisteredProductQueue.offer(product);
          try {
            Thread.sleep(250);
          } catch (InterruptedException ex) {
            e.printStackTrace();
          }
        }

      } else {
        // Queue is empty. Wait a bit, before checking again:
        try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override public boolean addToUnregisteredQueue(Object obj) throws IllegalArgumentException {
    if(!(obj instanceof ProductDto))
      throw new IllegalArgumentException("obj is not an instance of ProductDto");

    return unregisteredProductQueue.offer((ProductDto) obj);
  }

  @Override public List<Object> copyRegisteredQueue() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
