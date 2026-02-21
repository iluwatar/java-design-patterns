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

package com.iluwatar.productservice.controller;

import com.iluwatar.productservice.model.Product;
import com.iluwatar.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Product operations.
 * Provides endpoints for managing products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Get all products.
   *
   * @return list of all products
   */
  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  /**
   * Get product by ID.
   *
   * @param id the product ID
   * @return the product if found, 404 otherwise
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
    Optional<Product> product = productService.getProductById(id);
    return product.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Add a new product.
   *
   * @param product the product to add
   * @return the created product
   */
  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
  }

  /**
   * Delete a product by ID.
   *
   * @param id the product ID
   * @return 204 if deleted, 404 if not found
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
    boolean deleted = productService.deleteProduct(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * Health check endpoint.
   *
   * @return service status
   */
  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Product Service is running!");
  }
}
