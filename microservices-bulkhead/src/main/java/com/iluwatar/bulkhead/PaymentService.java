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
package com.iluwatar.bulkhead;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * Simulates a payment provider that has become slow. Every call takes the configured latency, so a
 * burst of payment requests keeps the threads that serve it busy for a long time. Without a
 * bulkhead these calls would also occupy the threads needed by healthy dependencies.
 */
@Slf4j
public class PaymentService implements RemoteService {

  private final Duration latency;

  /**
   * Creates a payment service.
   *
   * @param latency time every call takes to complete
   */
  public PaymentService(Duration latency) {
    this.latency = latency;
  }

  @Override
  public String call(String request) {
    LOGGER.info("Payment provider received '{}', it will take {} ms", request, latency.toMillis());
    try {
      Thread.sleep(latency);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Payment for '" + request + "' was interrupted", e);
    }
    return "Payment approved for " + request;
  }
}
