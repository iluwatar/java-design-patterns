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
package com.iluwatar.product.microservice;

import com.iluwatar.product.microservice.microservice.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductControllerTest {

  private ProductController productController;

  @BeforeEach
  public void setUp() {
    productController = new ProductController();
  }

  /**
   * Test to validate the product.
   */
  @Test
  void testValidateProduct() {
    // Arrange
    String request = "Sample product validation request";
    // Act
    ResponseEntity<Boolean> response = productController.validateProduct(request);
    // Assert
    assertEquals(ResponseEntity.ok(true), response);
  }

  /**
   * Test to validate the product with null request.
   */
  @Test
  void testValidateProductWithNullRequest() {
    // Arrange
    // Act
    ResponseEntity<Boolean> response = productController.validateProduct(null);
    // Assert
    assertEquals(ResponseEntity.ok(true), response);
  }
}
