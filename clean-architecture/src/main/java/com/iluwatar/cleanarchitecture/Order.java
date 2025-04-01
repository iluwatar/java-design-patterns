package com.iluwatar.cleanarchitecture;

import java.util.List;

public class Order {
  private String orderId;
  private List<Cart> items;
  private double totalPrice;

  public Order(String orderId, List<Cart> items) {
    this.orderId = orderId;
    this.items = items;
    this.totalPrice = items.stream().mapToDouble(Cart::getTotalPrice).sum();
  }
  public String getOrderId() {
    return orderId;
  }

  public List<Cart> getItems() {
    return items;
  }

  public double getTotalPrice() {
    return totalPrice;
  }
}
