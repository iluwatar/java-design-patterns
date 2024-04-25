package com.iluwatar.commander;

public record RetryParams(int numOfRetries, long retryDuration) {
  public static final RetryParams DEFAULT = new RetryParams(3, 30000L);
}