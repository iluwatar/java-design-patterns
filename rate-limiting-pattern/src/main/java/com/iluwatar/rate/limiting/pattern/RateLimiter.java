package com.iluwatar.rate.limiting.pattern;

/** Base interface for all rate limiter strategies. */
public interface RateLimiter {
  /**
   * Checks if a request is allowed under current rate limits
   *
   * @param serviceName Service being called (e.g., "dynamodb")
   * @param operationName Operation being performed (e.g., "Query")
   * @throws RateLimitException if request exceeds limits
   */
  void check(String serviceName, String operationName) throws RateLimitException;
}
