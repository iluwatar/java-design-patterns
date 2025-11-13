/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.strategy.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * PaymentProcessor 테스트.
 */
class PaymentProcessorTest {

  @Test
  void testCreditCardPaymentSuccess() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When
    PaymentResult result = processor.processPayment(1000.0);

    // Then
    assertTrue(result.isSuccess());
    assertNotNull(result.getTransactionId());
    assertTrue(result.getTransactionId().startsWith("CC-"));
  }

  @Test
  void testCreditCardPaymentInsufficientLimit() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When
    PaymentResult result = processor.processPayment(6000.0);

    // Then
    assertFalse(result.isSuccess());
    assertEquals("Insufficient credit limit", result.getMessage());
  }

  @Test
  void testBankTransferPaymentSuccess() {
    // Given
    PaymentStrategy bankTransfer = new BankTransferPayment(
        "KB Bank", "1234567890", "John Doe"
    );
    PaymentProcessor processor = new PaymentProcessor(bankTransfer);

    // When
    PaymentResult result = processor.processPayment(10000.0);

    // Then
    assertTrue(result.isSuccess());
    assertNotNull(result.getTransactionId());
    assertTrue(result.getTransactionId().startsWith("BT-"));
  }

  @Test
  void testPayPalPaymentSuccess() {
    // Given
    PaymentStrategy paypal = new PayPalPayment("user@example.com", "password123");
    PaymentProcessor processor = new PaymentProcessor(paypal);

    // When
    PaymentResult result = processor.processPayment(500.0);

    // Then
    assertTrue(result.isSuccess());
    assertNotNull(result.getTransactionId());
    assertTrue(result.getTransactionId().startsWith("PP-"));
  }

  @Test
  void testSwitchPaymentStrategy() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When - 신용카드로 결제
    PaymentResult result1 = processor.processPayment(1000.0);

    // Then
    assertTrue(result1.isSuccess());
    assertEquals("Credit Card", processor.getCurrentPaymentMethod());

    // When - PayPal로 변경
    PaymentStrategy paypal = new PayPalPayment("user@example.com", "password123");
    processor.setPaymentStrategy(paypal);
    PaymentResult result2 = processor.processPayment(500.0);

    // Then
    assertTrue(result2.isSuccess());
    assertEquals("PayPal", processor.getCurrentPaymentMethod());
  }

  @Test
  void testInvalidPaymentAmount() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When
    PaymentResult result = processor.processPayment(-100.0);

    // Then
    assertFalse(result.isSuccess());
    assertEquals("Invalid payment amount", result.getMessage());
  }

  @Test
  void testPaymentExceedsLimit() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When
    PaymentResult result = processor.processPayment(15000.0);

    // Then
    assertFalse(result.isSuccess());
    assertTrue(result.getMessage().contains("exceeds limit"));
  }

  @Test
  void testCanProcessPayment() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When & Then
    assertTrue(processor.canProcessPayment(1000.0));
    assertTrue(processor.canProcessPayment(5000.0));
    assertFalse(processor.canProcessPayment(15000.0));
  }

  @Test
  void testDifferentPaymentMethods() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentStrategy bankTransfer = new BankTransferPayment(
        "KB Bank", "1234567890", "John Doe"
    );
    PaymentStrategy paypal = new PayPalPayment("user@example.com", "password123");

    // When & Then
    assertEquals("Credit Card", creditCard.getPaymentMethodName());
    assertEquals("Bank Transfer (KB Bank)", bankTransfer.getPaymentMethodName());
    assertEquals("PayPal", paypal.getPaymentMethodName());
  }

  @Test
  void testPaymentResultFields() {
    // Given
    PaymentStrategy creditCard = new CreditCardPayment(
        "1234567890123456", "John Doe", "123", "12/25"
    );
    PaymentProcessor processor = new PaymentProcessor(creditCard);

    // When
    PaymentResult result = processor.processPayment(1000.0);

    // Then
    assertTrue(result.isSuccess());
    assertNotNull(result.getMessage());
    assertNotNull(result.getTransactionId());
    assertNotNull(result.getTimestamp());
  }
}
