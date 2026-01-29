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
package com.iluwatar.payment.microservice

// ABOUTME: Unit tests for PaymentController REST endpoint functionality.
// ABOUTME: Verifies payment processing with various request scenarios.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

/** Payment controller test. */
class PaymentControllerTest {
    private lateinit var paymentController: PaymentController

    @BeforeEach
    fun setUp() {
        paymentController = PaymentController()
    }

    /** Test to process the payment. */
    @Test
    fun testValidateProduct() {
        // Arrange
        val request = "Sample payment process request"
        // Act
        val response = paymentController.payment(request)
        // Assert
        assertEquals(ResponseEntity.ok(true), response)
    }

    /** Test to process the payment with null request. */
    @Test
    fun testValidateProductWithNullRequest() {
        // Arrange
        // Act
        val response = paymentController.payment(null)
        // Assert
        assertEquals(ResponseEntity.ok(true), response)
    }
}
