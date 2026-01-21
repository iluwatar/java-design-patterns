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
package com.iluwatar.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for {@link InventoryService}.
 * Tests service behavior with various message types.
 */
class InventoryServiceTest {

  private InventoryService inventoryService;

  @BeforeEach
  void setUp() {
    inventoryService = new InventoryService();
  }

  @Test
  void testHandleOrderCreatedMessage() {
    // Arrange
    var message = new Message("Order Created: ORDER-001");

    // Act & Assert
    assertDoesNotThrow(() -> inventoryService.handleMessage(message),
        "Should handle order created message without error");
  }

  @Test
  void testHandleOrderCancelledMessage() {
    // Arrange
    var message = new Message("Order Cancelled: ORDER-001");

    // Act & Assert
    assertDoesNotThrow(() -> inventoryService.handleMessage(message),
        "Should handle order cancelled message without error");
  }

  @Test
  void testHandleOrderUpdatedMessage() {
    // Arrange
    var message = new Message("Order Updated: ORDER-001");

    // Act & Assert
    assertDoesNotThrow(() -> inventoryService.handleMessage(message),
        "Should handle order updated message without error");
  }

  @Test
  void testHandleUnknownMessage() {
    // Arrange
    var message = new Message("Unknown Event: ORDER-001");

    // Act & Assert
    assertDoesNotThrow(() -> inventoryService.handleMessage(message),
        "Should handle unknown message without error");
  }

  @Test
  void testHandleMultipleMessages() {
    // Act & Assert
    assertDoesNotThrow(() -> {
      inventoryService.handleMessage(new Message("Order Created: ORDER-001"));
      inventoryService.handleMessage(new Message("Order Updated: ORDER-001"));
      inventoryService.handleMessage(new Message("Order Cancelled: ORDER-001"));
    }, "Should handle multiple messages without error");
  }

  @Test
  void testHandleMessageWithNullContent() {
    // Arrange
    var message = new Message(null);

    // Act & Assert
    assertDoesNotThrow(() -> inventoryService.handleMessage(message),
        "Should handle message with null content gracefully");
  }
}
