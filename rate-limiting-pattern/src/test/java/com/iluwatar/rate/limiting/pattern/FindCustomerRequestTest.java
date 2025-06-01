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

    FindCustomerRequest request = new FindCustomerRequest("999", noOpLimiter) {
      @Override
      public String execute() throws RateLimitException {
        Thread.currentThread().interrupt(); // Simulate thread interruption
        return super.execute(); // Should throw ServiceUnavailableException
      }
    };

    assertThrows(ServiceUnavailableException.class, request::execute);
  }
}
