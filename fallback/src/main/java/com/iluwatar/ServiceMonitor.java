package com.iluwatar;

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ServiceMonitor class provides monitoring capabilities for tracking service performance metrics.
 * It maintains thread-safe counters for success, fallback, and error rates, as well as timing information.
 */
public class ServiceMonitor {
  private final AtomicInteger successCount = new AtomicInteger(0);
  private final AtomicInteger fallbackCount = new AtomicInteger(0);
  private final AtomicInteger errorCount = new AtomicInteger(0);
  private final AtomicReference<Instant> lastSuccessTime = new AtomicReference<>(Instant.now());
  private final AtomicReference<Instant> lastFailureTime = new AtomicReference<>(Instant.now());
  private final AtomicReference<Duration> lastResponseTime = new AtomicReference<>(Duration.ZERO);
  
  // Add sliding window for metrics
  private final Queue<ServiceMetric> metrics = new ConcurrentLinkedQueue<>();
  private final Duration metricWindow = Duration.ofMinutes(5);
  
  private record ServiceMetric(Instant timestamp, MetricType type, Duration responseTime) {}
  private enum MetricType { SUCCESS, FALLBACK, ERROR }
  
  /**
   * Records a successful service operation with its response time.
   *
   * @param responseTime the duration of the successful operation
   */
  public void recordSuccess(Duration responseTime) {
    successCount.incrementAndGet();
    lastSuccessTime.set(Instant.now());
    lastResponseTime.set(responseTime);
    metrics.offer(new ServiceMetric(Instant.now(), MetricType.SUCCESS, responseTime));
    pruneOldMetrics();
  }
  
  /**
   * Records a fallback operation in the monitoring metrics.
   * This method increments the fallback counter and adds a new metric to the sliding window.
   */
  public void recordFallback() {
    fallbackCount.incrementAndGet();
    lastFailureTime.set(Instant.now());
    metrics.offer(new ServiceMetric(Instant.now(), MetricType.FALLBACK, Duration.ZERO));
    pruneOldMetrics();
  }
  
  /**
   * Records an error operation in the monitoring metrics.
   * This method increments the error counter, updates the last failure time,
   * and adds a new metric to the sliding window.
   */
  public void recordError() {
    errorCount.incrementAndGet();
    lastFailureTime.set(Instant.now());
    metrics.offer(new ServiceMetric(Instant.now(), MetricType.ERROR, Duration.ZERO));
    pruneOldMetrics();
  }
  
  public int getSuccessCount() {
    return successCount.get();
  }
  
  public int getFallbackCount() {
    return fallbackCount.get();
  }
  
  public int getErrorCount() {
    return errorCount.get();
  }
  
  public Instant getLastSuccessTime() {
    return lastSuccessTime.get();
  }
  
  public Instant getLastFailureTime() {
    return lastFailureTime.get();
  }
  
  public Duration getLastResponseTime() {
    return lastResponseTime.get();
  }
  
  /**
   * Calculates the success rate of service operations.
   * The rate is calculated as the ratio of successful operations to total operations,
   * treating fallbacks as partial successes.
   *
   * @return the success rate as a double between 0.0 and 1.0
   */
  public double getSuccessRate() {
    int total = successCount.get() + errorCount.get();
    if (total == 0) {
      return 1.0; // Return 1.0 for initial state with no requests
    }
    return (double) successCount.get() / total;
  }
  
  /**
   * Resets all monitoring statistics to their initial values.
   * This includes resetting all counters to zero and setting timestamps to current time.
   */
  public void reset() {
    successCount.set(0);
    fallbackCount.set(0);
    errorCount.set(0);
    lastSuccessTime.set(Instant.now());
    lastFailureTime.set(Instant.now());
    lastResponseTime.set(Duration.ZERO);
    metrics.clear();
  }
  
  private void pruneOldMetrics() {
    Instant cutoff = Instant.now().minus(metricWindow);
    metrics.removeIf(metric -> metric.timestamp.isBefore(cutoff));
  }
}
