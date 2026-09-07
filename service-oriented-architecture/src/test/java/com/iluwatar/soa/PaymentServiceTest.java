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
package com.iluwatar.soa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

  private final PaymentService service = new PaymentService(100.0);

  @Test
  void shouldChargeWithinCreditLimitAndIssueUniqueReferences() {
    var first = charge(60.0);
    var second = charge(100.0);

    assertTrue(first.success());
    assertTrue(second.success());
    assertEquals("PAY-1", first.body());
    assertEquals("PAY-2", second.body());
  }

  @Test
  void shouldDeclineChargeAboveCreditLimit() {
    var response = charge(100.5);

    assertFalse(response.success());
    assertEquals("Payment of 100.5 declined for C-1: exceeds credit limit", response.message());
  }

  @Test
  void shouldDeclineNonPositiveAmount() {
    var response = charge(0.0);

    assertFalse(response.success());
    assertEquals("Amount must be positive: 0.0", response.message());
  }

  @Test
  void shouldFailForUnknownOperation() {
    var response = service.handle(new ServiceRequest(PaymentService.NAME, "refund", Map.of()));

    assertFalse(response.success());
    assertEquals("Unknown operation: refund", response.message());
  }

  private ServiceResponse charge(double amount) {
    return service.handle(
        new ServiceRequest(
            PaymentService.NAME, "charge", Map.of("customerId", "C-1", "amount", amount)));
  }
}
