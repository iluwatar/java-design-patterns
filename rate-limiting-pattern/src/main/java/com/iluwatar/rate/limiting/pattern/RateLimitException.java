package com.iluwatar.rate.limiting.pattern;

/**
 * Base exception for rate limiting errors.
 */
public class RateLimitException extends Exception {
  private final long retryAfterMillis;

  public RateLimitException(String message, long retryAfterMillis) {
    super(message);
    this.retryAfterMillis = retryAfterMillis;
  }

  public long getRetryAfterMillis() {
    return retryAfterMillis;
  }
}