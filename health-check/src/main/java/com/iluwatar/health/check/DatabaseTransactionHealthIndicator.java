package com.iluwatar.health.check;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * A health indicator that checks the health of database transactions by attempting to perform a
 * test transaction using a retry mechanism. If the transaction succeeds after multiple attempts,
 * the health indicator returns {@link Health#up()} and logs a success message. If all retry
 * attempts fail, the health indicator returns {@link Health#down()} and logs an error message.
 *
 * @author ydoksanbir
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Setter
@Getter
public class DatabaseTransactionHealthIndicator implements HealthIndicator {

  /** A repository for performing health checks on the database. */
  private final HealthCheckRepository healthCheckRepository;

  /** An asynchronous health checker used to execute health checks in a separate thread. */
  private final AsynchronousHealthChecker asynchronousHealthChecker;

  /** A retry template used to retry the test transaction if it fails due to a transient error. */
  private final RetryTemplate retryTemplate;

  /**
   * The timeout in seconds for the health check. If the health check does not complete within this
   * timeout, it will be considered timed out and will return {@link Health#down()}.
   */
  @Value("${health.check.timeout:10}")
  private long timeoutInSeconds;

  /**
   * Performs a health check by attempting to perform a test transaction with retry support.
   *
   * @return the health status of the database transactions
   */
  @Override
  public Health health() {
    LOGGER.info("Calling performCheck with timeout {}", timeoutInSeconds);
    Supplier<Health> dbTransactionCheck =
        () -> {
          try {
            healthCheckRepository.performTestTransaction();
            return Health.up().build();
          } catch (Exception e) {
            LOGGER.error("Database transaction health check failed", e);
            return Health.down(e).build();
          }
        };
    try {
      return asynchronousHealthChecker.performCheck(dbTransactionCheck, timeoutInSeconds).get();
    } catch (InterruptedException | ExecutionException e) {
      LOGGER.error("Database transaction health check timed out or was interrupted", e);
      Thread.currentThread().interrupt();
      return Health.down(e).build();
    }
  }
}
