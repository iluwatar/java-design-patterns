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

import java.util.Map;

/** A {@link ProductCatalog} backed by a fixed price list that the composition root supplies. */
public class InMemoryProductCatalog implements ProductCatalog {

  private final Map<String, Long> pricesInCents;

  /**
   * Creates a catalog from a price list.
   *
   * @param pricesInCents unit price in cents for each SKU
   */
  public InMemoryProductCatalog(Map<String, Long> pricesInCents) {
    this.pricesInCents = Map.copyOf(pricesInCents);
  }

  @Override
  public long unitPriceInCents(String sku) {
    var price = pricesInCents.get(sku);
    if (price == null) {
      throw new IllegalArgumentException("Unknown product: " + sku);
    }
    return price;
  }
}
