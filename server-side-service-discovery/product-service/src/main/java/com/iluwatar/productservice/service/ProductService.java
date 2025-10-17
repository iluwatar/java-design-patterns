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

package com.iluwatar.productservice.service;

import com.iluwatar.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for managing products.
 * Provides business logic for product operations.
 */
@Service
public class ProductService {

  private final List<Product> products = new ArrayList<>();

  /**
   * Constructor that initializes some sample products.
   */
  public ProductService() {
    products.add(new Product(1L, "Laptop", "High-performance laptop", 999.99, "Electronics"));
    products.add(new Product(2L, "Book", "Java Design Patterns", 49.99, "Books"));
    products.add(new Product(3L, "Coffee Mug", "Programmer's coffee mug", 19.99, "Office Supplies"));
  }

  /**
   * Get all products.
   *
   * @return list of all products
   */
  public List<Product> getAllProducts() {
    return new ArrayList<>(products);
  }

  /**
   * Get product by ID.
   *
   * @param id the product ID
   * @return the product if found, empty otherwise
   */
  public Optional<Product> getProductById(Long id) {
    return products.stream()
        .filter(product -> product.getId().equals(id))
        .findFirst();
  }

  /**
   * Add a new product.
   *
   * @param product the product to add
   * @return the added product
   */
  public Product addProduct(Product product) {
    if (product.getId() == null) {
      Long nextId = products.stream()
          .mapToLong(Product::getId)
          .max()
          .orElse(0) + 1;
      product.setId(nextId);
    }
    products.add(product);
    return product;
  }

  /**
   * Delete a product by ID.
   *
   * @param id the product ID
   * @return true if product was deleted, false otherwise
   */
  public boolean deleteProduct(Long id) {
    return products.removeIf(product -> product.getId().equals(id));
  }
}
