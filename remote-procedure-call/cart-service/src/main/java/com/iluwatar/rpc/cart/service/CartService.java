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
package com.iluwatar.rpc.cart.service;

import com.iluwatar.rpc.cart.model.ProductInCart;

/**
 * API Contract for Cart service, which can be exposed.
 * Includes contract method for grpc stub as well.
 * Thread safety is not guaranteed.
 * @link com.iluwatar.rpc.cart.model.ProductInCart
 * @author CoderSleek
 * @version 1.0
 */
public interface CartService {
  /**
   * returns a random product from cart, if cart is empty throws IllegalStateException.
   * randomized just for demonstration purposes.
   *
   * @return randomly chosen ProductInCart from List of ProductInCart
   * @throws IllegalStateException if cart is empty
   */
  ProductInCart getRandomProductFromCart() throws IllegalStateException;

  /**
   * reduces the quantity of a product from cart by doing a RPC call to product service.
   *
   * @param product product whose quantity needs to be reduced in product-service
   * @throws IllegalArgumentException if product is null or id invalid
   * @throws IllegalStateException if product is not found in cart or cart is null
   */
  void reduceCartQuantityFromProduct(ProductInCart product)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * RPC call to get all the products from product-service and add them to cart.
   */
  void getAllProducts();
}
