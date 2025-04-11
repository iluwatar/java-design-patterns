package com.learning.contextservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Clock;

@Component("myCustomHealthCheck")
public class MyCustomHealthCheck implements HealthIndicator {

  private static final Logger log = LoggerFactory.getLogger(MyCustomHealthCheck.class);

  private volatile boolean isHealthy = true;

  @Scheduled(fixedRate = 5000) // Run every 5 seconds
  public void updateHealthStatus() {
    // Perform checks here to determine the current health
    // For example, check database connectivity, external service availability, etc.
    isHealthy = performHealthCheck();
    log.info("Update health status : {}", isHealthy);
  }

  boolean performHealthCheck() {
    boolean current = System.currentTimeMillis() % 10000 < 5000; // Simulate fluctuating health
    log.debug("Performing health check, current status: {}", current);
    return current; // Simulate fluctuating health
  }

  @Override
  public Health health() {
    if (isHealthy) {
      log.info("Health check successful, service is UP");
      return Health.up().withDetail("message", "Service is running and scheduled checks are OK").build();
    } else {
      log.warn("Health check failed, service is DOWN");
      return Health.down().withDetail("error", "Scheduled health checks failed").build();
    }
  }
}
