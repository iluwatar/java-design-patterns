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

// ABOUTME: Unit tests for OrderService business logic and external service integration.
// ABOUTME: Tests order processing, product validation, and payment processing with MockK.

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

/** OrderServiceTest class to test the OrderService. */
class OrderServiceTest {
    private lateinit var orderService: OrderService
    private lateinit var restTemplateBuilder: RestTemplateBuilder
    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        restTemplateBuilder = mockk()
        restTemplate = mockk()
        every { restTemplateBuilder.build() } returns restTemplate
        orderService = OrderService(restTemplateBuilder)
    }

    /** Test to process the order successfully. */
    @Test
    fun testProcessOrderSuccess() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(true)
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30301/payment/process"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(true)
        // Act
        val result = orderService.processOrder()
        // Assert
        assertEquals("Order processed successfully", result)
    }

    /** Test to process the order with failure caused by product validation failure. */
    @Test
    fun testProcessOrderFailureWithProductValidationFailure() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(false)
        // Act
        val result = orderService.processOrder()
        // Assert
        assertEquals("Order processing failed", result)
    }

    /** Test to process the order with failure caused by payment processing failure. */
    @Test
    fun testProcessOrderFailureWithPaymentProcessingFailure() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(true)
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30301/payment/process"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(false)
        // Act
        val result = orderService.processOrder()
        // Assert
        assertEquals("Order processing failed", result)
    }

    /** Test to validate the product. */
    @Test
    fun testValidateProduct() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(true)
        // Act
        val result = orderService.validateProduct()
        // Assert
        assertEquals(true, result)
    }

    /** Test to process the payment. */
    @Test
    fun testProcessPayment() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30301/payment/process"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } returns ResponseEntity.ok(true)
        // Act
        val result = orderService.processPayment()
        // Assert
        assertEquals(true, result)
    }

    /** Test to validate the product with ResourceAccessException. */
    @Test
    fun testValidateProductResourceAccessException() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } throws ResourceAccessException("Service unavailable")
        // Act
        val result = orderService.validateProduct()
        // Assert
        assertEquals(false, result)
    }

    /** Test to validate the product with HttpClientErrorException. */
    @Test
    fun testValidateProductHttpClientErrorException() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30302/product/validate"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } throws HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request")
        // Act
        val result = orderService.validateProduct()
        // Assert
        assertEquals(false, result)
    }

    /** Test to process the payment with ResourceAccessException. */
    @Test
    fun testProcessPaymentResourceAccessException() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30301/payment/process"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } throws ResourceAccessException("Service unavailable")
        // Act
        val result = orderService.processPayment()
        // Assert
        assertEquals(false, result)
    }

    /** Test to process the payment with HttpClientErrorException. */
    @Test
    fun testProcessPaymentHttpClientErrorException() {
        // Arrange
        every {
            restTemplate.postForEntity(
                eq("http://localhost:30301/payment/process"),
                any<String>(),
                eq(Boolean::class.java),
            )
        } throws HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request")
        // Act
        val result = orderService.processPayment()
        // Assert
        assertEquals(false, result)
    }
}
