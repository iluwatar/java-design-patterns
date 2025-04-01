/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
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

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the {@link ProductRepository} interface.
 *
 * <p>This repository stores products in memory
 * allowing retrieval by product ID.</p>
 */
public class InMemoryProductRepository implements ProductRepository {
  /**
   * A map to store products by their unique product ID.
   */
  private final Map<String, Product> products = new HashMap<>();
  /**
   * The price of the Laptop in USD.
   * <p>Used in the in-memory product repository
   * to define the cost of a Laptop.</p>
   */
  private static final double LAPTOP_PRICE = 1000.0;

  /**
   * The price of the Smartphone in USD.
   * <p>Used in the in-memory product repository
   * to define the cost of a Smartphone.</p>
   */
  private static final double SMARTPHONE_PRICE = 500.0;


  /**
   * Constructs an {@code InMemoryProductRepository} and
   * initializes it with some example products.
   */
  public InMemoryProductRepository() {
    products.put("1", new Product("1", "Laptop", LAPTOP_PRICE));
    products.put("2", new Product("2", "Smartphone", SMARTPHONE_PRICE));
  }

  /**
   * Retrieves a product by its unique ID.
   *
   * @param productId The ID of the product to retrieve.
   * @return The {@link Product} corresponding to the given ID
   * {@code null} if not found.
   */
  @Override
  public Product getProductById(final String productId) {
    return products.get(productId);
  }
}
