package com.iluwatar.api.gateway;

/**
 * Encapsulates all of the information that mobile client needs to display a product.
 */
public class MobileProduct {
  /**
   * The price of the product
   */
  private String price;

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
