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
package com.iluwatar.cleanarchitecture;

import lombok.Getter;

/**
 * Represents a shopping cart containing a product and its quantity. This class calculates the total
 * price of the product based on its price and quantity.
 */
@Getter
public class Cart {
  /** The product in the cart. It holds the product details such as name, price, and description. */
  private final Product product;

  /**
   * The quantity of the product in the cart. It represents how many units of the product are added
   * to the cart.
   */
  private final int quantity;

  /**
   * Constructs a new Cart instance with a specified product and quantity.
   *
   * @param prod the product to be added to the cart.
   * @param qty the quantity of the product in the cart.
   */
  public Cart(final Product prod, final int qty) {
    this.product = prod;
    this.quantity = qty;
  }

  /**
   * Calculates the total price of the products in the cart. The total price is the product's price
   * multiplied by the quantity.
   *
   * @return the total price of the products in the cart.
   */
  public double getTotalPrice() {
        int getTotPrice = product.getPrice() * quantity;
        return getTotPrice;
  }
}
