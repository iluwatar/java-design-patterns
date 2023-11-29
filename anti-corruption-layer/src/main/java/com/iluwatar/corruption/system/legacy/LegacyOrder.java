package com.iluwatar.corruption.system.legacy;

import java.util.Objects;

/**
 * The class represents an order in the legacy system.
 * The class is used by the legacy system to store the data.
 */
public class LegacyOrder {
  private String id;
  private String customer;

  private String item;
  private int qty;
  private int price;

  /**
   * Constructor.
   * @param id the id of the order
   * @param customer the customer of the order
   * @param item the item of the order
   * @param qty the quantity of the order
   * @param price the price of the order
   */
  public LegacyOrder(String id, String customer, String item, int qty, int price) {
    this.id = id;
    this.customer = customer;
    this.item = item;
    this.qty = qty;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Order{"
        + "id='"
        + id
        + '\''
        + ", customer='"
        + customer + '\''
        + ", item='" + item + '\''
        + ", qty='" + qty + '\''
        + ", price='" + price + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LegacyOrder legacyOrder = (LegacyOrder) o;

    if (!Objects.equals(id, legacyOrder.id)) {
      return false;
    }
    if (!Objects.equals(customer, legacyOrder.customer)) {
      return false;
    }
    if (!Objects.equals(item, legacyOrder.item)) {
      return false;
    }
    if (!Objects.equals(qty, legacyOrder.qty)) {
      return false;
    }
    return Objects.equals(price, legacyOrder.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customer, item, qty, price);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
