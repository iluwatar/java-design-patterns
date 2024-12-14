/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.sessionfacade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Product test.
 */
public class ProductTest {
  /**
   * Test product creation.
   */
  @Test
  public void testProductCreation() {
    int id = 1;
    String name = "Product A";
    double price = 200.0;
    String description = "a description";
    Product product = new Product(id,name,price,description);
    assertEquals(id, product.id());
    assertEquals(name, product.name());
    assertEquals(price, product.price());
    assertEquals(description, product.description());
  }

  /**
   * Test equals and hash code.
   */
  @Test
  public void testEqualsAndHashCode() {
    Product product1 = new Product(1, "Product A", 99.99, "a description");
    Product product2 = new Product(1, "Product A", 99.99, "a description");
    Product product3 = new Product(2, "Product B", 199.99, "a description");

    assertEquals(product1, product2);
    assertNotEquals(product1, product3);
    assertEquals(product1.hashCode(), product2.hashCode());
    assertNotEquals(product1.hashCode(), product3.hashCode());
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    Product product = new Product(1, "Product A", 99.99, "a description");
    String toStringResult = product.toString();
    assertTrue(toStringResult.contains("Product A"));
    assertTrue(toStringResult.contains("99.99"));
  }

}
