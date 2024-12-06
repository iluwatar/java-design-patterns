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
import java.util.ArrayList;
import java.util.List;

/**
 * The type Shopping facade.
 */
public class ShoppingFacade {
  /**
   * The Product catalog.
   */
  List<Product> productCatalog = new ArrayList<>();
  /**
   * The Cart.
   */
  List<Product> cart = new ArrayList<>();
  /**
   * The Cart service.
   */
  CartService cartService = new CartService(cart, productCatalog);
  /**
   * The Order service.
   */
  OrderService orderService = new OrderService(cart);
  /**
   * The Payment service.
   */
  PaymentService paymentService = new PaymentService();

  /**
   * Instantiates a new Shopping facade.
   */
  public ShoppingFacade() {
    productCatalog = new ArrayList<>();
    productCatalog.add(new Product(1, "Wireless Mouse", 25.99, "Ergonomic wireless mouse with USB receiver."));
    productCatalog.add(new Product(2, "Gaming Keyboard", 79.99, "RGB mechanical gaming keyboard with programmable keys."));
    cart = new ArrayList<>();
    cartService = new CartService(cart, productCatalog);
    orderService = new OrderService(cart);
    paymentService = new PaymentService();
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
   * Select payment method.
   *
   * @param method the method
   */
  public void selectPaymentMethod(String method) {
    this.paymentService.selectPaymentMethod(method);
  }
}
