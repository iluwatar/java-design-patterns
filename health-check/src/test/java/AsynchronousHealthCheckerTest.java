import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.iluwatar.health.check.AsynchronousHealthChecker;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

/**
 * Tests for {@link AsynchronousHealthChecker}.
 *
 * @author ydoksanbir
 */
@Slf4j
class AsynchronousHealthCheckerTest {

  /** The {@link AsynchronousHealthChecker} instance to be tested. */
  private AsynchronousHealthChecker healthChecker;

  private ListAppender<ILoggingEvent> listAppender;

  /**
   * Sets up the test environment before each test method.
   *
   * <p>Creates a new {@link AsynchronousHealthChecker} instance.
   */
  @BeforeEach
  void setUp() {
    healthChecker = new AsynchronousHealthChecker();
    // Replace the logger with the root logger of logback
    LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    // Create and start a ListAppender
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    listAppender = new ListAppender<>();
    listAppender.start();

    // Add the appender to the root logger context
    loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(listAppender);
  }

  /**
   * Tears down the test environment after each test method.
   *
   * <p>Shuts down the {@link AsynchronousHealthChecker} instance to prevent resource leaks.
   */
  @AfterEach
  void tearDown() {
    healthChecker.shutdown();
    ((LoggerContext) LoggerFactory.getILoggerFactory()).reset();
  }

  /**
   * Tests that the {@link performCheck()} method completes normally when the health supplier
   * returns a successful health check.
   *
   * <p>Given a health supplier that returns a healthy status, the test verifies that the {@link
   * performCheck()} method completes normally and returns the expected health object.
   */
  @Test
  void whenPerformCheck_thenCompletesNormally() throws ExecutionException, InterruptedException {
    // Given
    Supplier<Health> healthSupplier = () -> Health.up().build();

    // When
    CompletableFuture<Health> healthFuture = healthChecker.performCheck(healthSupplier, 3);

    // Then
    Health health = healthFuture.get();
    assertEquals(Health.up().build(), health);
  }

  /**
   * Tests that the {@link performCheck()} method returns a healthy health status when the health
   * supplier returns a healthy status.
   *
   * <p>Given a health supplier that returns a healthy status, the test verifies that the {@link
   * performCheck()} method returns a health object with a status of UP.
   */
  @Test
  void whenHealthCheckIsSuccessful_ReturnsHealthy()
      throws ExecutionException, InterruptedException {
    // Arrange
    Supplier<Health> healthSupplier = () -> Health.up().build();

    // Act
    CompletableFuture<Health> healthFuture = healthChecker.performCheck(healthSupplier, 4);

    // Assert
    assertEquals(Status.UP, healthFuture.get().getStatus());
  }

  /**
   * Tests that the {@link performCheck()} method rejects new tasks after the {@link shutdown()}
   * method is called.
   *
   * <p>Given the {@link AsynchronousHealthChecker} instance is shut down, the test verifies that
   * the {@link performCheck()} method throws a {@link RejectedExecutionException} when attempting
   * to submit a new health check task.
   */
  @Test
  void whenShutdown_thenRejectsNewTasks() {
    // Given
    healthChecker.shutdown();

    // When/Then
    assertThrows(
        RejectedExecutionException.class,
        () -> healthChecker.performCheck(() -> Health.up().build(), 2),
        "Expected to throw RejectedExecutionException but did not");
  }

  /**
   * Tests that the {@link performCheck()} method returns a healthy health status when the health
   * supplier returns a healthy status.
   *
   * <p>Given a health supplier that throws a RuntimeException, the test verifies that the {@link
   * performCheck()} method returns a health object with a status of DOWN and an error message
   * containing the exception message.
   */
  @Test
  void whenHealthCheckThrowsException_thenReturnsDown() {
    // Arrange
    Supplier<Health> healthSupplier =
        () -> {
          throw new RuntimeException("Health check failed");
        };
    // Act
    CompletableFuture<Health> healthFuture = healthChecker.performCheck(healthSupplier, 10);
    // Assert
    Health health = healthFuture.join();
    assertEquals(Status.DOWN, health.getStatus());
    String errorMessage = health.getDetails().get("error").toString();
    assertTrue(errorMessage.contains("Health check failed"));
  }

  /**
   * Helper method to check if the log contains a specific message.
   *
   * @param action The action that triggers the log statement.
   * @return True if the log contains the message after the action is performed, false otherwise.
   */
  private boolean doesLogContainMessage(Runnable action) {
    action.run();
    List<ch.qos.logback.classic.spi.ILoggingEvent> events = listAppender.list;
    return events.stream()
        .anyMatch(event -> event.getMessage().contains("Health check executor did not terminate"));
  }

  /**
   * Tests that the {@link AsynchronousHealthChecker#shutdown()} method logs an error message when
   * the executor does not terminate after attempting to cancel tasks.
   */
  @Test
  void whenShutdownExecutorDoesNotTerminateAfterCanceling_LogsErrorMessage() {
    // Given
    AsynchronousHealthChecker healthChecker = new AsynchronousHealthChecker();
    healthChecker.shutdown(); // To trigger the scenario

    // When/Then
    boolean containsMessage = doesLogContainMessage(healthChecker::shutdown);
    if (!containsMessage) {
      List<ch.qos.logback.classic.spi.ILoggingEvent> events = listAppender.list;
      LOGGER.info("Logged events:");
      for (ch.qos.logback.classic.spi.ILoggingEvent event : events) {
        LOGGER.info(event.getMessage());
      }
    }
    assertTrue(containsMessage, "Expected log message not found");
  }
}
