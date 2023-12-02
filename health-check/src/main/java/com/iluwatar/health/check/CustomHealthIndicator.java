package com.iluwatar.health.check;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A custom health indicator that periodically checks the health of a database and caches the
 * result. It leverages an asynchronous health checker to perform the health checks.
 *
 * @author ydoksanbir
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

  private final AsynchronousHealthChecker healthChecker;
  private final CacheManager cacheManager;
  private final HealthCheckRepository healthCheckRepository;

  @Value("${health.check.timeout:10}")
  private long timeoutInSeconds;

  /**
   * Perform a health check and cache the result.
   *
   * @return the health status of the application
   * @throws HealthCheckInterruptedException if the health check is interrupted
   */
  @Override
  @Cacheable(value = "health-check", unless = "#result.status == 'DOWN'")
  public Health health() {
    LOGGER.info("Performing health check");
    CompletableFuture<Health> healthFuture =
        healthChecker.performCheck(this::check, timeoutInSeconds);
    try {
      return healthFuture.get(timeoutInSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Health check interrupted", e);
      throw new HealthCheckInterruptedException(e);
    } catch (Exception e) {
      LOGGER.error("Health check failed", e);
      return Health.down(e).build();
    }
  }

  /**
   * Checks the health of the database by querying for a simple constant value expected from the
   * database.
   *
   * @return Health indicating UP if the database returns the constant correctly, otherwise DOWN.
   */
  private Health check() {
    Integer result = healthCheckRepository.checkHealth();
    boolean databaseIsUp = result != null && result == 1;
    LOGGER.info("Health check result: {}", databaseIsUp);
    return databaseIsUp
        ? Health.up().withDetail("database", "reachable").build()
        : Health.down().withDetail("database", "unreachable").build();
  }

  /**
   * Evicts all entries from the health check cache. This is scheduled to run at a fixed rate
   * defined in the application properties.
   */
  @Scheduled(fixedRateString = "${health.check.cache.evict.interval:60000}")
  public void evictHealthCache() {
    LOGGER.info("Evicting health check cache");
    try {
      Cache healthCheckCache = cacheManager.getCache("health-check");
      LOGGER.info("Health check cache: {}", healthCheckCache);
      if (healthCheckCache != null) {
        healthCheckCache.clear();
      }
    } catch (Exception e) {
      LOGGER.error("Failed to evict health check cache", e);
    }
  }
}
