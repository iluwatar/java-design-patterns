package com.iluwatar.verticalslicearchitecture.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

  @Test
  void testProductConstructorAndGetters() {
    Integer id = 1;
    Double price = 100.0;
    String name = "Sample Product";

    Product product = Product.builder().id(id).price(price).name(name).build();

    assertEquals(id, product.getId());
    assertEquals(price, product.getPrice());
    assertEquals(name, product.getName());
  }
}
