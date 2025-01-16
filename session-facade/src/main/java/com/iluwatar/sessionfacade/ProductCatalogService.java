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

/**
 * The type ProductCatalogService.
 * This class manages a catalog of products. It holds a map of products,
 * where each product is identified by a unique ID. The class
 * provides functionality to access and manage the products in the catalog.
 */
public class ProductCatalogService {

  private final Map<Integer, Product> products;

  /**
   * Instantiates a new ProductCatalogService.
   *
   * @param products the map of products to be used by this service
   */
  public ProductCatalogService(Map<Integer, Product> products) {
    this.products = products;
  }

  // Additional methods to interact with products can be added here, for example:

  /**
   * Retrieves a product by its ID.
   *
   * @param id the product ID
   * @return the product corresponding to the ID
   */
  public Product getProductById(int id) {
    return products.get(id);
  }
}
