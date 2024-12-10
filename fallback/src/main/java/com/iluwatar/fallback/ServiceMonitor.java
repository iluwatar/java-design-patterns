package com.iluwatar.fallback;

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
  
  // Sliding window for metrics
  private final Queue<ServiceMetric> metrics = new ConcurrentLinkedQueue<>();
  
  private record ServiceMetric(Instant timestamp, MetricType type, Duration responseTime) {}
  private enum MetricType { SUCCESS, FALLBACK, ERROR }

  /**
   * Weight applied to fallback operations when calculating success rate.
   * Values between 0.0 and 1.0:
   * - 1.0 means fallbacks count as full successes
   * - 0.0 means fallbacks count as failures
   * - Default 0.7 indicates fallbacks are "partial successes" since they provide
   *   degraded but still functional service to users
   */
  private final double fallbackWeight;

  /**
   * Duration for which metrics should be retained.
   * Metrics older than this window will be removed during pruning.
   */
  private final Duration metricWindow;

  /**
   * Constructs a ServiceMonitor with default configuration.
   * Uses default fallback weight of 0.7 and 5-minute metric window.
   */
  public ServiceMonitor() {
    this(0.7, Duration.ofMinutes(5));
  }

  /**
   * Constructs a ServiceMonitor with the specified fallback weight and metric window.
   *
   * @param fallbackWeight weight applied to fallback operations (0.0 to 1.0)
   * @param metricWindow duration for which metrics should be retained
   * @throws IllegalArgumentException if parameters are invalid
   */
  public ServiceMonitor(double fallbackWeight, Duration metricWindow) {
    if (fallbackWeight < 0.0 || fallbackWeight > 1.0) {
      throw new IllegalArgumentException("Fallback weight must be between 0.0 and 1.0");
    }
    if (metricWindow.isNegative() || metricWindow.isZero()) {
      throw new IllegalArgumentException("Metric window must be positive");
    }
    this.fallbackWeight = fallbackWeight;
    this.metricWindow = metricWindow;
  }

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
   * This method increments the fallback counter and updates metrics.
   * Fallbacks are considered as degraded successes.
   */
  public void recordFallback() {
    fallbackCount.incrementAndGet();
    Instant now = Instant.now();
    Duration responseTime = lastResponseTime.get();
    metrics.offer(new ServiceMetric(now, MetricType.FALLBACK, responseTime));
    pruneOldMetrics();
  }
  
  /**
   * Records an error operation in the monitoring metrics.
   * This method increments the error counter, updates the last failure time,
   * and adds a new metric to the sliding window.
   */
  public void recordError() {
    errorCount.incrementAndGet();
    Instant now = Instant.now();
    lastFailureTime.set(now);
    Duration responseTime = lastResponseTime.get();
    metrics.offer(new ServiceMetric(now, MetricType.ERROR, responseTime));
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
   * The rate is calculated as successes / total attempts,
   * where both fallbacks and errors count as failures.
   *
   * @return the success rate as a double between 0.0 and 1.0
   */
  public double getSuccessRate() {
    int successes = successCount.get();
    int fallbacks = fallbackCount.get();
    int errors = errorCount.get();
    int totalAttempts = successes + fallbacks + errors;
    
    if (totalAttempts == 0) {
      return 0.0;
    }
    
    // Weight fallbacks as partial successes since they provide degraded but functional service
    return (successes + (fallbacks * fallbackWeight)) / totalAttempts;
  }
  
  /**
   * Resets all monitoring metrics to their initial values.
   * This includes success count, error count, fallback count, and timing statistics.
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
  
  /**
   * Removes metrics that are older than the configured time window.
   * This method is called automatically after each new metric is added
   * to maintain a sliding window of recent metrics and prevent unbounded
   * memory growth.
   */
  private void pruneOldMetrics() {
    Instant cutoff = Instant.now().minus(metricWindow);
    metrics.removeIf(metric -> metric.timestamp.isBefore(cutoff));
  }
}

