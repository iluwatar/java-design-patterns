package com.iluwatar.api.gateway;

/**
 * The ApiGateway aggregates calls to microservices based on the needs of the individual clients.
 */
public class ApiGateway {

  private ImageService imageService = new ImageService();
  private PriceService priceService = new PriceService();

  /**
   * Retrieves product information that desktop clients need
   * @return Product information for clients on a desktop
   */
  public DesktopProduct getProductDesktop() {
    DesktopProduct desktopProduct = new DesktopProduct();
    desktopProduct.setImagePath(imageService.getImagePath());
    desktopProduct.setPrice(priceService.getPrice());
    return desktopProduct;
  }

  /**
   * Retrieves product information that mobile clients need
   * @return Product information for clients on a mobile device
   */
  public MobileProduct getProductMobile() {
    MobileProduct mobileProduct = new MobileProduct();
    mobileProduct.setPrice(priceService.getPrice());
    return mobileProduct;
  }
}
