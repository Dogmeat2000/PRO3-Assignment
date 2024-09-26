package shared.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product
{
  private String product_id;
  private List<AnimalPart> contentList;
  private List<Tray> traySupplyList;

  public Product() {
    setProduct_id("NotSpecified");
    contentList = new ArrayList<>();
    traySupplyList = new ArrayList<>();
  }

  public String getProduct_id() {
    return product_id;
  }

  public void setProduct_id(String product_id) {
    this.product_id = product_id;
  }

  public List<AnimalPart> getContentList() {
    return contentList;
  }

  public List<Tray> getTraySupplyList() {
    return traySupplyList;
  }


  @Override public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass())
      return false;

    return Objects.equals(getProduct_id(), ((Product) o).getProduct_id())
        && Objects.equals(getContentList(), ((Product) o).getContentList()) //TODO Confirm that this equals method also performs equals check on contents.
        && Objects.equals(getTraySupplyList(), ((Product) o).getTraySupplyList()); //TODO Confirm that this equals method also performs equals check on contents.
  }

  @Override public String toString() {
    return "Product{" + "product_id='" + product_id + '\'' + ", contentList=" + contentList + ", traySupplyList=" + traySupplyList + '}';
  }
}
