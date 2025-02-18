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


import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Cart service.
 * Represents the cart entity, has add to cart and remove from cart methods
 */
@Slf4j
public class CartService {
  /**
   * -- GETTER --
   *  Gets cart.
   */
  @Getter
  private final Map<Integer, Product> cart;
  private final Map<Integer, Product> productCatalog;

  /**
   * Instantiates a new Cart service.
   *
   * @param cart           the cart
   * @param productCatalog the product catalog
   */
  public CartService(Map<Integer, Product> cart, Map<Integer, Product> productCatalog) {
    this.cart = cart;
    this.productCatalog = productCatalog;
  }

  /**
   * Add to cart.
   *
   * @param productId the product id
   */
  public void addToCart(int productId) {
    Product product = productCatalog.get(productId);
    if (product != null) {
      cart.put(productId, product);
      LOGGER.info("{} successfully added to the cart", product);
    } else {
      LOGGER.info("No product is found in catalog with id {}", productId);
    }
  }

  /**
   * Remove from cart.
   *
   * @param productId the product id
   */
  public void removeFromCart(int productId) {
    Product product = cart.remove(productId); // Remove product from cart
    if (product != null) {
      LOGGER.info("{} successfully removed from the cart", product);
    } else {
      LOGGER.info("No product is found in cart with id {}", productId);
    }
  }

}
