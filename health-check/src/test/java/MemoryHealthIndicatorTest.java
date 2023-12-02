import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iluwatar.health.check.AsynchronousHealthChecker;
import com.iluwatar.health.check.MemoryHealthIndicator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

/**
 * Unit tests for {@link MemoryHealthIndicator}.
 *
 * @author ydoksanbir
 */
@ExtendWith(MockitoExtension.class)
class MemoryHealthIndicatorTest {

  /** Mocked AsynchronousHealthChecker instance. */
  @Mock private AsynchronousHealthChecker asynchronousHealthChecker;

  /** `MemoryHealthIndicator` instance to be tested. */
  @InjectMocks private MemoryHealthIndicator memoryHealthIndicator;

  /**
   * Test case for the `health()` method when memory usage is below the threshold.
   *
   * <p>Asserts that when the `health()` method is called and memory usage is below the threshold,
   * it returns a Health object with Status.UP.
   */
  @Test
  void whenMemoryUsageIsBelowThreshold_thenHealthIsUp() {
    // Arrange
    CompletableFuture<Health> future =
        CompletableFuture.completedFuture(
            Health.up().withDetail("memory usage", "50% of max").build());
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), anyLong())).thenReturn(future);

    // Act
    Health health = memoryHealthIndicator.health();

    // Assert
    assertEquals(Status.UP, health.getStatus());
    assertEquals("50% of max", health.getDetails().get("memory usage"));
  }

  /**
   * Test case for the `health()` method when memory usage is above the threshold.
   *
   * <p>Asserts that when the `health()` method is called and memory usage is above the threshold,
   * it returns a Health object with Status.DOWN.
   */
  @Test
  void whenMemoryUsageIsAboveThreshold_thenHealthIsDown() {
    // Arrange
    CompletableFuture<Health> future =
        CompletableFuture.completedFuture(
            Health.down().withDetail("memory usage", "95% of max").build());
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), anyLong())).thenReturn(future);

    // Act
    Health health = memoryHealthIndicator.health();

    // Assert
    assertEquals(Status.DOWN, health.getStatus());
    assertEquals("95% of max", health.getDetails().get("memory usage"));
  }

  /**
   * Test case for the `health()` method when the health check is interrupted.
   *
   * <p>Asserts that when the `health()` method is called and the health check is interrupted, it
   * returns a Health object with Status DOWN and an error detail indicating the interruption.
   *
   * @throws ExecutionException if the future fails to complete
   * @throws InterruptedException if the thread is interrupted while waiting for the future to
   *     complete
   */
  @Test
  void whenHealthCheckIsInterrupted_thenHealthIsDown()
      throws ExecutionException, InterruptedException {
    // Arrange
    CompletableFuture<Health> future = mock(CompletableFuture.class);
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), anyLong())).thenReturn(future);
    // Simulate InterruptedException when future.get() is called
    when(future.get()).thenThrow(new InterruptedException("Health check interrupted"));

    // Act
    Health health = memoryHealthIndicator.health();

    // Assert
    assertEquals(Status.DOWN, health.getStatus());
    String errorDetail = (String) health.getDetails().get("error");
    assertNotNull(errorDetail);
    assertTrue(errorDetail.contains("Health check interrupted"));
  }

  /**
   * Test case for the `health()` method when the health check execution fails.
   *
   * <p>Asserts that when the `health()` method is called and the health check execution fails, it
   * returns a Health object with Status DOWN and an error detail indicating the failure.
   */
  @Test
  void whenHealthCheckExecutionFails_thenHealthIsDown() {
    // Arrange
    CompletableFuture<Health> future = new CompletableFuture<>();
    future.completeExceptionally(
        new ExecutionException(new RuntimeException("Service unavailable")));
    when(asynchronousHealthChecker.performCheck(any(Supplier.class), anyLong())).thenReturn(future);

    // Act
    Health health = memoryHealthIndicator.health();

    // Assert
    assertEquals(Status.DOWN, health.getStatus());
    assertTrue(health.getDetails().get("error").toString().contains("Service unavailable"));
  }
}
