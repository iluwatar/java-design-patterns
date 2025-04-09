package com.learning.greetingservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("myCustomHealthCheck")
@Slf4j
public class MyCustomHealthCheck implements HealthIndicator {

  private volatile boolean isHealthy = true;

  @Scheduled(fixedRate = 5000) // Run every 5 seconds
  public void updateHealthStatus() {
    // Perform checks here to determine the current health
    // For example, check database connectivity, external service availability, etc.
    boolean currentHealth = performHealthCheck();
    isHealthy = currentHealth;
    log.info("Updated health status : {}", isHealthy);
  }

  private boolean performHealthCheck() {
    // Replace this with your actual health check logic
    // For demonstration, let's toggle the status every few runs
    boolean current = System.currentTimeMillis() % 10000 < 5000;
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
