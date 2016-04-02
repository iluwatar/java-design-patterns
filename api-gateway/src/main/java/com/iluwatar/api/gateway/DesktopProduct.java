package com.iluwatar.api.gateway;

/**
 * Encapsulates all of the information that a desktop client needs to display a product.
 */
public class DesktopProduct {
  /**
   * The price of the product
   */
  private String price;

  /**
   * The path to the image of the product
   */
  private String imagePath;

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
