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

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


/**
 * The ShoppingFacade class provides a simplified interface for clients to interact with the shopping system.
 * It acts as a facade to handle operations related to a shopping cart, order processing, and payment.
 * Responsibilities:
 * - Add products to the shopping cart.
 * - Remove products from the shopping cart.
 * - Retrieve the current shopping cart.
 * - Finalize an order by calling the order service.
 * - Check if a payment is required based on the order total.
 * - Process payment using different payment methods (e.g., cash, credit card).
 * The ShoppingFacade class delegates operations to the following services:
 * - CartService: Manages the cart and product catalog.
 * - OrderService: Handles the order finalization process and calculation of the total.
 * - PaymentService: Handles the payment processing based on the selected payment method.
 */
@Slf4j
public class ShoppingFacade {
  private final CartService cartService;
  private final OrderService orderService;
  private final PaymentService paymentService;

  /**
   * Instantiates a new Shopping facade.
   */
  public ShoppingFacade() {
    Map<Integer, Product> productCatalog = new HashMap<>();
    productCatalog.put(1, new Product(1, "Wireless Mouse", 25.99, "Ergonomic wireless mouse with USB receiver."));
    productCatalog.put(2, new Product(2, "Gaming Keyboard", 79.99, "RGB mechanical gaming keyboard with programmable keys."));
    Map<Integer, Product> cart = new HashMap<>();
    cartService = new CartService(cart, productCatalog);
    orderService = new OrderService(cart);
    paymentService = new PaymentService();
  }

  /**
   * Gets cart.
   *
   * @return the cart
   */
  public Map<Integer, Product> getCart() {
    return this.cartService.getCart();
  }

  /**
   * Add to cart.
   *
   * @param productId the product id
   */
  public void addToCart(int productId) {
    this.cartService.addToCart(productId);
  }

  /**
   * Remove from cart.
   *
   * @param productId the product id
   */
  public void removeFromCart(int productId) {
    this.cartService.removeFromCart(productId);
  }

  /**
   * Order.
   */
  public void order() {
    this.orderService.order();
  }

  /**
   * Is payment required boolean.
   *
   * @return the boolean
   */
  public Boolean isPaymentRequired() {
    double total = this.orderService.getTotal();
    if (total == 0.0) {
      LOGGER.info("No payment required");
      return false;
    }
    return true;
  }

  /**
   * Process payment.
   *
   * @param method the method
   */
  public void processPayment(String method) {
    Boolean isPaymentRequired = isPaymentRequired();
    if (Boolean.TRUE.equals(isPaymentRequired)) {
      paymentService.selectPaymentMethod(method);
    }
  }
}
