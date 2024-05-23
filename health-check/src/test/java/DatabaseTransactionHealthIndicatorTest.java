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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.iluwatar.health.check.AsynchronousHealthChecker;
import com.iluwatar.health.check.DatabaseTransactionHealthIndicator;
import com.iluwatar.health.check.HealthCheckRepository;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.retry.support.RetryTemplate;

/**
 * Unit tests for the {@link DatabaseTransactionHealthIndicator} class.
 *
 */
class DatabaseTransactionHealthIndicatorTest {

  /** Timeout value in seconds for the health check. */
  private final long timeoutInSeconds = 4;

  /** Mocked HealthCheckRepository instance. */
  @Mock private HealthCheckRepository healthCheckRepository;

  /** Mocked AsynchronousHealthChecker instance. */
  @Mock private AsynchronousHealthChecker asynchronousHealthChecker;

  /** Mocked RetryTemplate instance. */
  @Mock private RetryTemplate retryTemplate;

  /** `DatabaseTransactionHealthIndicator` instance to be tested. */
  private DatabaseTransactionHealthIndicator healthIndicator;

  /** Performs initialization before each test method. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    healthIndicator =
        new DatabaseTransactionHealthIndicator(
            healthCheckRepository, asynchronousHealthChecker, retryTemplate);
    healthIndicator.setTimeoutInSeconds(timeoutInSeconds);
  }

  /**
   * Test case for the `health()` method when the database transaction succeeds.
   *
   * <p>Asserts that when the `health()` method is called and the database transaction succeeds, it
   * returns a Health object with Status.UP.
   */
  @Test
  void whenDatabaseTransactionSucceeds_thenHealthIsUp() throws Exception {
    CompletableFuture<Health> future = CompletableFuture.completedFuture(Health.up().build());
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), eq(timeoutInSeconds)))
        .thenReturn(future);

    // Simulate the health check repository behavior
    doNothing().when(healthCheckRepository).performTestTransaction();

    // Now call the actual method
    Health health = healthIndicator.health();

    // Check that the health status is UP
    assertEquals(Status.UP, health.getStatus());
  }

  /**
   * Test case for the `health()` method when the database transaction fails.
   *
   * <p>Asserts that when the `health()` method is called and the database transaction fails, it
   * returns a Health object with Status.DOWN.
   */
  @Test
  void whenDatabaseTransactionFails_thenHealthIsDown() throws Exception {
    CompletableFuture<Health> future = new CompletableFuture<>();
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), eq(timeoutInSeconds)))
        .thenReturn(future);

    // Simulate a database exception during the transaction
    doThrow(new RuntimeException("DB exception"))
        .when(healthCheckRepository)
        .performTestTransaction();

    // Complete the future exceptionally to simulate a failure in the health check
    future.completeExceptionally(new RuntimeException("DB exception"));

    Health health = healthIndicator.health();

    // Check that the health status is DOWN
    assertEquals(Status.DOWN, health.getStatus());
  }

  /**
   * Test case for the `health()` method when the health check times out.
   *
   * <p>Asserts that when the `health()` method is called and the health check times out, it returns
   * a Health object with Status.DOWN.
   */
  @Test
  void whenHealthCheckTimesOut_thenHealthIsDown() {
    CompletableFuture<Health> future = new CompletableFuture<>();
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), eq(timeoutInSeconds)))
        .thenReturn(future);

    // Complete the future exceptionally to simulate a timeout
    future.completeExceptionally(new RuntimeException("Simulated timeout"));

    Health health = healthIndicator.health();

    // Check that the health status is DOWN due to timeout
    assertEquals(Status.DOWN, health.getStatus());
  }
}
