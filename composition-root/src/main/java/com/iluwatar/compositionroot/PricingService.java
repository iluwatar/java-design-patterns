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
package com.iluwatar.compositionroot;

/**
 * Prices an order line. It receives its {@link ProductCatalog} through the constructor and never
 * decides which catalog implementation to use.
 */
public class PricingService {

  private final ProductCatalog catalog;

  /**
   * Creates the service.
   *
   * @param catalog where unit prices come from
   */
  public PricingService(ProductCatalog catalog) {
    this.catalog = catalog;
  }

  /**
   * Returns the price of an order line.
   *
   * @param sku the product
   * @param quantity how many units, at least one
   * @return the price in cents
   * @throws ArithmeticException if the price overflows a {@code long}
   */
  public long priceInCents(String sku, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("Quantity must be at least 1, was " + quantity);
    }
    return Math.multiplyExact(catalog.unitPriceInCents(sku), quantity);
  }
}
