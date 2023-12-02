package com.iluwatar.health.check;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * A custom health indicator that checks the memory usage of the application and reports the health
 * status accordingly. It uses an asynchronous health checker to perform the health check and a
 * configurable memory usage threshold to determine the health status.
 *
 * @author ydoksanbir
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemoryHealthIndicator implements HealthIndicator {

  private final AsynchronousHealthChecker asynchronousHealthChecker;

  /** The timeout in seconds for the health check. */
  @Value("${health.check.timeout:10}")
  private long timeoutInSeconds;

  /**
   * The memory usage threshold in percentage. If the memory usage is less than this threshold, the
   * health status is reported as UP. Otherwise, the health status is reported as DOWN.
   */
  @Value("${health.check.memory.threshold:0.9}")
  private double memoryThreshold;

  /**
   * Performs a health check by checking the memory usage of the application.
   *
   * @return the health status of the application
   */
  public Health checkMemory() {
    Supplier<Health> memoryCheck =
        () -> {
          MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
          MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
          long maxMemory = heapMemoryUsage.getMax();
          long usedMemory = heapMemoryUsage.getUsed();

          double memoryUsage = (double) usedMemory / maxMemory;
          String format = String.format("%.2f%% of %d max", memoryUsage * 100, maxMemory);

          if (memoryUsage < memoryThreshold) {
            LOGGER.info("Memory usage is below threshold: {}", format);
            return Health.up().withDetail("memory usage", format).build();
          } else {
            return Health.down().withDetail("memory usage", format).build();
          }
        };

    try {
      CompletableFuture<Health> future =
          asynchronousHealthChecker.performCheck(memoryCheck, timeoutInSeconds);
      return future.get();
    } catch (InterruptedException e) {
      LOGGER.error("Health check interrupted", e);
      Thread.currentThread().interrupt();
      return Health.down().withDetail("error", "Health check interrupted").build();
    } catch (ExecutionException e) {
      LOGGER.error("Health check failed", e);
      Throwable cause = e.getCause() == null ? e : e.getCause();
      return Health.down().withDetail("error", cause.toString()).build();
    }
  }

  /**
   * Retrieves the health status of the application by checking the memory usage.
   *
   * @return the health status of the application
   */
  @Override
  public Health health() {
    return checkMemory();
  }
}
