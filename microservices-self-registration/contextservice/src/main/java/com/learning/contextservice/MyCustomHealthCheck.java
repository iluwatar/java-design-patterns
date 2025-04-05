package com.learning.contextservice;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("myCustomHealthCheck")
public class MyCustomHealthCheck implements HealthIndicator {

  private volatile boolean isHealthy = true;

  @Scheduled(fixedRate = 5000) // Run every 5 seconds
  public void updateHealthStatus() {
    // Perform checks here to determine the current health
    // For example, check database connectivity, external service availability, etc.
    boolean currentHealth = performHealthCheck();
    isHealthy = currentHealth;
    System.out.println("Updated health status: " + isHealthy); // For logging
  }

  private boolean performHealthCheck() {
    // Replace this with your actual health check logic
    // For demonstration, let's toggle the status every few runs
    return System.currentTimeMillis() % 10000 < 5000; // Simulate fluctuating health
  }

  @Override
  public Health health() {
    if (isHealthy) {
      return Health.up().withDetail("message", "Service is running and scheduled checks are OK").build();
    } else {
      return Health.down().withDetail("error", "Scheduled health checks failed").build();
    }
  }
}
