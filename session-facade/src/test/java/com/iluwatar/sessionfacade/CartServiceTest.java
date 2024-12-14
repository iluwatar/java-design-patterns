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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Cart service test.
 */
@Slf4j
class CartServiceTest {

  private CartService cartService;
  private Map<Integer,Product> cart;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    cart = new HashMap<>();
    Map<Integer,Product> productCatalog = new HashMap<>();
    productCatalog.put(1,new Product(1, "Product A", 2.0, "any description"));
    productCatalog.put(2,new Product(2, "Product B", 300.0, "a watch"));
    cartService = new CartService(cart, productCatalog);
  }

  /**
   * Test add to cart.
   */
  @Test
  void testAddToCart() {
    cartService.addToCart(1);
    assertEquals(1, cart.size());
    assertEquals("Product A", cart.get(1).name());
  }

  /**
   * Test remove from cart.
   */
  @Test
  void testRemoveFromCart() {
    cartService.addToCart(1);
    assertEquals(1, cart.size());
    cartService.removeFromCart(1);
    assertTrue(cart.isEmpty());
  }

  /**
   * Test add to cart with invalid product id.
   */
  @Test
  void testAddToCartWithInvalidProductId() {
    cartService.addToCart(999);
    assertTrue(cart.isEmpty());
  }

  /**
   * Test remove from cart with invalid product id.
   */
  @Test
  void testRemoveFromCartWithInvalidProductId() {
    cartService.removeFromCart(999);
    assertTrue(cart.isEmpty());
  }
}
