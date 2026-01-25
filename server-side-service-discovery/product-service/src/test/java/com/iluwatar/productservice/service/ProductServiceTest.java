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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductService.
 */
class ProductServiceTest {

  private ProductService productService;

  @BeforeEach
  void setUp() {
    productService = new ProductService();
  }

  @Test
  void testGetAllProducts() {
    List<Product> products = productService.getAllProducts();
    assertNotNull(products);
    assertEquals(3, products.size());
  }

  @Test
  void testGetProductById() {
    Optional<Product> product = productService.getProductById(1L);
    assertTrue(product.isPresent());
    assertEquals("Laptop", product.get().getName());
  }

  @Test
  void testGetProductByIdNotFound() {
    Optional<Product> product = productService.getProductById(999L);
    assertFalse(product.isPresent());
  }

  @Test
  void testAddProduct() {
    Product newProduct = new Product(null, "Tablet", "Android tablet", 299.99, "Electronics");
    Product addedProduct = productService.addProduct(newProduct);
    
    assertNotNull(addedProduct.getId());
    assertEquals("Tablet", addedProduct.getName());
    assertEquals(4, productService.getAllProducts().size());
  }

  @Test
  void testDeleteProduct() {
    boolean deleted = productService.deleteProduct(1L);
    assertTrue(deleted);
    assertEquals(2, productService.getAllProducts().size());
  }

  @Test
  void testDeleteProductNotFound() {
    boolean deleted = productService.deleteProduct(999L);
    assertFalse(deleted);
    assertEquals(3, productService.getAllProducts().size());
  }
}
