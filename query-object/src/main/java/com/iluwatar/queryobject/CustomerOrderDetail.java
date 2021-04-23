package com.iluwatar.queryobject;

/**
 * This class represents the model of customer's order detail rows stored in a table in DBMS. It
 * stores the detail of each transactions in an order. The one-to-many relationship is also handled
 * similarly.
 */
public class CustomerOrderDetail {
  public final int quantity;
  public final double unitPrice;

  public CustomerOrderDetail(int quantity, double unitPrice) {
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }
}
