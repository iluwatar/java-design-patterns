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
package com.iluwatar.order.microservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * OrderServiceTest class to test the OrderService.
 */
class OrderServiceTest {

  @InjectMocks
  private OrderService orderService;

  @Mock
  private RestTemplateBuilder restTemplateBuilder;

  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
  }

  /**
   * Test to process the order successfully.
   */
  @Test
  void testProcessOrder_Success() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(true));
    when(restTemplate.postForEntity(eq("http://localhost:30301/payment/process"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(true));
    // Act
    String result = orderService.processOrder();
    // Assert
    assertEquals("Order processed successfully", result);
  }

  /**
   * Test to process the order with failure caused by product validation failure.
   */
  @Test
  void testProcessOrder_FailureWithProductValidationFailure() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(false));
    // Act
    String result = orderService.processOrder();
    // Assert
    assertEquals("Order processing failed", result);
  }

  /**
   * Test to process the order with failure caused by payment processing failure.
   */
  @Test
  void testProcessOrder_FailureWithPaymentProcessingFailure() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(true));
    when(restTemplate.postForEntity(eq("http://localhost:30301/payment/process"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(false));
    // Act
    String result = orderService.processOrder();
    // Assert
    assertEquals("Order processing failed", result);
  }

  /**
   * Test to validate the product.
   */
  @Test
  void testValidateProduct() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(true));
    // Act
    Boolean result = orderService.validateProduct();
    // Assert
    assertEquals(true, result);
  }

  /**
   * Test to process the payment.
   */
  @Test
  void testProcessPayment() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30301/payment/process"), anyString(), eq(Boolean.class)))
        .thenReturn(ResponseEntity.ok(true));
    // Act
    Boolean result = orderService.processPayment();
    // Assert
    assertEquals(true, result);
  }

  /**
   * Test to validate the product with ResourceAccessException.
   */
  @Test
  void testValidateProduct_ResourceAccessException() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenThrow(new ResourceAccessException("Service unavailable"));
    // Act
    Boolean result = orderService.validateProduct();
    // Assert
    assertEquals(false, result);
  }

  /**
   * Test to validate the product with HttpClientErrorException.
   */
  @Test
  void testValidateProduct_HttpClientErrorException() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30302/product/validate"), anyString(), eq(Boolean.class)))
        .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Bad request"));
    // Act
    Boolean result = orderService.validateProduct();
    // Assert
    assertEquals(false, result);
  }

  /**
   * Test to process the payment with ResourceAccessException.
   */
  @Test
  void testProcessPayment_ResourceAccessException() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30301/payment/process"), anyString(), eq(Boolean.class)))
        .thenThrow(new ResourceAccessException("Service unavailable"));
    // Act
    Boolean result = orderService.processPayment();
    // Assert
    assertEquals(false, result);
  }

  /**
   * Test to process the payment with HttpClientErrorException.
   */
  @Test
  void testProcessPayment_HttpClientErrorException() {
    // Arrange
    when(restTemplate.postForEntity(eq("http://localhost:30301/payment/process"), anyString(), eq(Boolean.class)))
        .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Bad request"));
    // Act
    Boolean result = orderService.processPayment();
    // Assert
    assertEquals(false, result);
  }
}
