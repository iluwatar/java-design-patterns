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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FindCustomerRequestTest implements RateLimitOperationTest<String> {

  @Override
  public RateLimitOperation<String> createOperation(RateLimiter limiter) {
    return new FindCustomerRequest("123", limiter);
  }

  @Test
  void shouldExecuteWhenUnderRateLimit() throws Exception {
    RateLimiter limiter = new TokenBucketRateLimiter(10, 10);
    RateLimitOperation<String> request = createOperation(limiter);

    String result = request.execute();
    assertEquals("Customer-123", result);
  }

  @Test
  void shouldThrowWhenRateLimitExceeded() {
    RateLimiter limiter = new TokenBucketRateLimiter(0, 0); // Always throttled
    RateLimitOperation<String> request = createOperation(limiter);

    assertThrows(RateLimitException.class, request::execute);
  }

  @Test
  void shouldReturnCorrectServiceAndOperationNames() {
    RateLimiter limiter = new TokenBucketRateLimiter(10, 10);
    FindCustomerRequest request = new FindCustomerRequest("123", limiter);

    assertEquals("CustomerService", request.getServiceName());
    assertEquals("FindCustomer", request.getOperationName());
  }

  // Reuse helper logic from the interface for coverage
  @Test
  void shouldExecuteUsingDefaultHelper() throws Exception {
    RateLimiter limiter = new TokenBucketRateLimiter(5, 5);
    shouldExecuteWhenUnderLimit(createOperation(limiter));
  }

  @Test
  void shouldThrowServiceUnavailableOnInterruptedException() {
    RateLimiter noOpLimiter = (service, operation) -> {}; // no throttling

    FindCustomerRequest request =
        new FindCustomerRequest("999", noOpLimiter) {
          @Override
          public String execute() throws RateLimitException {
            Thread.currentThread().interrupt(); // Simulate thread interruption
            return super.execute(); // Should throw ServiceUnavailableException
          }
        };

    assertThrows(ServiceUnavailableException.class, request::execute);
  }
}
