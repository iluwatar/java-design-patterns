package com.iluwatar.rate.limiting.pattern;

import java.util.concurrent.atomic.AtomicInteger;

public class AppTestUtils {

  public static void invokeMakeRequest(
      int clientId, RateLimiter limiter, String service, String operation) {
    App.makeRequest(clientId, limiter, service, operation);
  }

  public static void resetCounters() {
    App.resetCountersForTesting();
  }

  public static AtomicInteger getSuccessfulRequests() {
    return App.successfulRequests;
  }

  public static AtomicInteger getThrottledRequests() {
    return App.throttledRequests;
  }

  public static AtomicInteger getFailedRequests() {
    return App.failedRequests;
  }
}
