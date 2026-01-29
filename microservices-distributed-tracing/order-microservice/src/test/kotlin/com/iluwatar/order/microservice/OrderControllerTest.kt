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
package com.iluwatar.order.microservice

// ABOUTME: Unit tests for OrderController REST endpoint functionality.
// ABOUTME: Verifies order processing success and failure scenarios using MockK.

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** OrderControllerTest class to test the OrderController. */
class OrderControllerTest {
    private lateinit var orderController: OrderController
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setup() {
        orderService = mockk()
        orderController = OrderController(orderService)
    }

    /** Test to process the order successfully. */
    @Test
    fun processOrderShouldReturnSuccessStatus() {
        // Arrange
        every { orderService.processOrder() } returns "Order processed successfully"
        // Act
        val response = orderController.processOrder("test order")
        // Assert
        assertEquals("Order processed successfully", response.body)
    }

    /** Test to process the order with failure. */
    @Test
    fun processOrderShouldReturnFailureStatusWhen() {
        // Arrange
        every { orderService.processOrder() } returns "Order processing failed"
        // Act
        val response = orderController.processOrder("test order")
        // Assert
        assertEquals("Order processing failed", response.body)
    }
}
