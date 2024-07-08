package com.iluwatar.order.microservice;/*
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * OrderControllerTest class to test the OrderController.
 */
class OrderControllerTest {

  @InjectMocks
  private OrderController orderController;

  @Mock
  private OrderService orderService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test to process the order successfully.
   */
  @Test
  void processOrderShouldReturnSuccessStatus() {
    // Arrange
    when(orderService.processOrder()).thenReturn("Order processed successfully");
    // Act
    ResponseEntity<String> response = orderController.processOrder("test order");
    // Assert
    assertEquals("Order processed successfully", response.getBody());
  }

  /**
   * Test to process the order with failure.
   */
  @Test
  void ProcessOrderShouldReturnFailureStatusWhen() {
    // Arrange
    when(orderService.processOrder()).thenReturn("Order processing failed");
    // Act
    ResponseEntity<String> response = orderController.processOrder("test order");
    // Assert
    assertEquals("Order processing failed", response.getBody());
  }
}
