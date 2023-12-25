import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
 * @author ydoksanbir
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
  void whenDatabaseTransactionSucceeds_thenHealthIsUp() {
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
  void whenDatabaseTransactionFails_thenHealthIsDown() {
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
