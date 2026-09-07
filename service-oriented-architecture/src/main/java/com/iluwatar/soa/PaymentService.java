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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Enterprise service that charges customers.
 *
 * <p>Operations:
 *
 * <ul>
 *   <li>{@code charge} with {@code customerId} and {@code amount} returns a payment reference, or
 *       fails when the amount exceeds the credit limit
 * </ul>
 */
public class PaymentService implements Service {

  public static final String NAME = "payment";

  private final double creditLimit;
  private final AtomicInteger sequence = new AtomicInteger();

  /** Creates the service with a default credit limit. */
  public PaymentService() {
    this(1000.0);
  }

  /** Creates the service that declines any charge above the given limit. */
  public PaymentService(double creditLimit) {
    this.creditLimit = creditLimit;
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public ServiceResponse handle(ServiceRequest request) {
    return switch (request.operation()) {
      case "charge" -> charge(
          request.param("customerId", String.class), request.param("amount", Double.class));
      default -> ServiceResponse.error("Unknown operation: " + request.operation());
    };
  }

  private ServiceResponse charge(String customerId, double amount) {
    if (amount <= 0) {
      return ServiceResponse.error("Amount must be positive: " + amount);
    }
    if (amount > creditLimit) {
      return ServiceResponse.error(
          "Payment of " + amount + " declined for " + customerId + ": exceeds credit limit");
    }
    return ServiceResponse.ok("PAY-" + sequence.incrementAndGet());
  }
}
