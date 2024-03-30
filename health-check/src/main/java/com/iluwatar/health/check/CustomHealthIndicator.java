/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
