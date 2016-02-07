package com.iluwatar.api.gateway;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiGatewayTest {

  /**
   * Tests getting the data for a desktop client
   */
  @Test
  public void testGetProductDesktop() {
    ApiGateway apiGateway = new ApiGateway();
    DesktopProduct desktopProduct = apiGateway.getProductDesktop();

    assertEquals("20", desktopProduct.getPrice());
    assertEquals("/product-image.png", desktopProduct.getImagePath());
  }

  /**
   * Tests getting the data for a mobile client
   */
  @Test
  public void testGetProductMobile() {
    ApiGateway apiGateway = new ApiGateway();
    MobileProduct mobileProduct = apiGateway.getProductMobile();

    assertEquals("20", mobileProduct.getPrice());
  }
}
