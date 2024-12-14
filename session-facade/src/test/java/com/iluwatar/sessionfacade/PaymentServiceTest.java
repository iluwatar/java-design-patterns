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
package com.iluwatar.sessionfacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

/**
 * The type Payment service test.
 */
class PaymentServiceTest {
  private PaymentService paymentService;
  private OrderService orderService;
  private Logger mockLogger;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    paymentService = new PaymentService();
    mockLogger = mock(Logger.class);
    paymentService.LOGGER = mockLogger;
  }

  /**
   * Test select cash payment method.
   */
  @Test
  void testSelectCashPaymentMethod() {
    String method = "cash";
    paymentService.selectPaymentMethod(method);
    verify(mockLogger).info("Client have chosen cash payment option");
  }

  /**
   * Test select credit card payment method.
   */
  @Test
  void testSelectCreditCardPaymentMethod() {
    String method = "credit";
    paymentService.selectPaymentMethod(method);
    verify(mockLogger).info("Client have chosen credit card payment option");
  }

  /**
   * Test select unspecified payment method.
   */
  @Test
  void testSelectUnspecifiedPaymentMethod() {
    String method = "cheque";
    paymentService.selectPaymentMethod(method);
    verify(mockLogger).info("Unspecified payment method type");
  }
}
