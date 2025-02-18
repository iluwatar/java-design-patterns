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
package com.iluwatar.rpc.product.service;

import com.iluwatar.rpc.product.mocks.ProductMocks;
import com.iluwatar.rpc.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
  private static List<Product> products;
  private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    products = ProductMocks.getMockProducts();
    productService = new ProductServiceImpl(products);
  }

  @Test
  void testGetProduct() {
    Optional<Product> product = productService.getProduct(1L);
    assertTrue(product.isPresent());
    assertEquals(1L, product.get().getId());
    assertEquals("Product 1", product.get().getName());
    assertEquals(50.0, product.get().getPrice());
  }

  @Test
  void testGetProductNotFound() {
    Optional<Product> product = productService.getProduct(6L);
    assertFalse(product.isPresent());
  }

  @Test
  void testGetAllProducts() {
    List<Product> allProducts = productService.getAllProducts();
    assertNotNull(allProducts);
    assertEquals(5, allProducts.size());
  }

  @Test
  void testGetAllProductsEmpty() {
    products.clear();
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      productService.getAllProducts();
    });
    assertEquals("Products array does not have any data", exception.getMessage());
  }

  @Test
  void testGetAllProductNull() {
    productService = new ProductServiceImpl(null);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      productService.getAllProducts();
    });
    assertEquals("Products array is not initialized properly", exception.getMessage());
  }
}