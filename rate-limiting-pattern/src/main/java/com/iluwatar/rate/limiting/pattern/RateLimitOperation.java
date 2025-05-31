package com.iluwatar.rate.limiting.pattern;

/**
 * Interface representing an operation that needs rate limiting.
 */
public interface RateLimitOperation<T> {
  String getServiceName();
  String getOperationName();
  T execute() throws RateLimitException;
}
