package com.iluwatar.cleanArchitecture;

public class Cart {
  private Product product;
  private int quantity;

  public Cart(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public double getTotalPrice() {
    return product.getPrice() * quantity;
  }
  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

}
