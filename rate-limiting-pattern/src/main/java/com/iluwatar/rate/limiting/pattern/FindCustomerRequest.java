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
package com.iluwatar.rate.limiting.pattern;

/**
 * A rate-limited customer lookup operation. This class wraps the rate limiting logic and represents
 * an executable business request.
 */
public class FindCustomerRequest implements RateLimitOperation<String> {
  private final String customerId;
  private final RateLimiter rateLimiter;

  public FindCustomerRequest(String customerId, RateLimiter rateLimiter) {
    this.customerId = customerId;
    this.rateLimiter = rateLimiter;
  }

  @Override
  public String getServiceName() {
    return "CustomerService";
  }

  @Override
  public String getOperationName() {
    return "FindCustomer";
  }

  @Override
  public String execute() throws RateLimitException {
    // Ensure the operation respects the assigned rate limiter
    rateLimiter.check(getServiceName(), getOperationName());

    // Simulate actual operation
    try {
      Thread.sleep(50); // Simulate processing time
      return "Customer-" + customerId;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ServiceUnavailableException(getServiceName(), 1000);
    }
  }
}
