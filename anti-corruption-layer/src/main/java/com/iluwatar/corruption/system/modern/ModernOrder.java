package com.iluwatar.corruption.system.modern;

import java.util.Objects;

/**
 * The class represents an order in the modern system.
 */
public class ModernOrder {
  private String id;
  private Customer customer;

  private Shipment shipment;

  private String extra;

  /**
   * Constructor.
   * @param id the id of the order
   * @param customer the customer of the order
   * @param shipment the shipment of the order
   * @param extra  the extra data of the order
   */
  public ModernOrder(String id, Customer customer, Shipment shipment, String extra) {
    this.id = id;
    this.customer = customer;
    this.shipment = shipment;
    this.extra = extra;
  }

  @Override
  public String toString() {
    return "ModernOrder{"
        + "id='"
        + id + '\''
        + ", customer=" + customer
        + ", shipment=" + shipment
        + ", extra='" + extra + '\''
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

    ModernOrder that = (ModernOrder) o;

    if (!Objects.equals(id, that.id)) {
      return false;
    }
    if (!Objects.equals(customer, that.customer)) {
      return false;
    }
    return Objects.equals(shipment, that.shipment);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (customer != null ? customer.hashCode() : 0);
    result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
    return result;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Shipment getShipment() {
    return shipment;
  }

  public void setShipment(Shipment shipment) {
    this.shipment = shipment;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }
}
