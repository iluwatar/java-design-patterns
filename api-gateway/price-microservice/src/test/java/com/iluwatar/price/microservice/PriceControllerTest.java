package com.iluwatar.price.microservice;

import org.junit.Assert;
import org.junit.Test;

public class PriceControllerTest {
  @Test
  public void testgetPrice() {
    PriceController priceController = new PriceController();

    String price = priceController.getPrice();

    Assert.assertEquals("20", price);
  }
}
