package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExceptionTests {
  @Test
  void rateLimitExceptionShouldContainRetryInfo() {
    RateLimitException exception = new RateLimitException("Test", 1000);
    assertEquals(1000, exception.getRetryAfterMillis());
    assertEquals("Test", exception.getMessage());
  }

  @Test
  void throttlingExceptionShouldContainServiceInfo() {
    ThrottlingException exception = new ThrottlingException("dynamodb", "Query", 500);
    assertEquals("dynamodb", exception.getServiceName());
    assertEquals("ThrottlingException", exception.getErrorCode());
  }

  @Test
  void serviceUnavailableExceptionShouldContainRetryInfo() {
    ServiceUnavailableException exception = new ServiceUnavailableException("s3", 2000);
    assertEquals("s3", exception.getServiceName());
    assertEquals(2000, exception.getRetryAfterMillis());
  }
}
