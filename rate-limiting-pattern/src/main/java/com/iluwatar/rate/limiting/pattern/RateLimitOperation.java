package com.iluwatar.rate.limiting.pattern;

/** Represents a business operation that needs rate limiting. Supports type-safe return values. */
public interface RateLimitOperation<T> {
  String getServiceName();

  String getOperationName();

  T execute() throws RateLimitException;
}
