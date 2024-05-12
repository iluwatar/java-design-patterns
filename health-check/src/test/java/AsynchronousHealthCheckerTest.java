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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.iluwatar.health.check.AsynchronousHealthChecker;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

  @Mock private ScheduledExecutorService executorService;

  public AsynchronousHealthCheckerTest() {
    MockitoAnnotations.openMocks(this);
  }

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
    List<ILoggingEvent> events = listAppender.list;
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

  /**
   * Verifies that {@link AsynchronousHealthChecker#awaitTerminationWithTimeout} returns true even
   * if the executor service does not terminate completely within the specified timeout.
   *
   * @throws NoSuchMethodException if the private method cannot be accessed.
   * @throws InvocationTargetException if the private method throws an exception.
   * @throws IllegalAccessException if the private method is not accessible.
   * @throws InterruptedException if the thread is interrupted while waiting for the executor
   *     service to terminate.
   */
  @Test
  void awaitTerminationWithTimeout_IncompleteTermination_ReturnsTrue()
      throws NoSuchMethodException,
          InvocationTargetException,
          IllegalAccessException,
          InterruptedException {

    // Mock executor service to return false (incomplete termination)
    when(executorService.awaitTermination(5, TimeUnit.SECONDS)).thenReturn(false);

    // Use reflection to access the private method for code coverage.
    Method privateMethod =
        AsynchronousHealthChecker.class.getDeclaredMethod("awaitTerminationWithTimeout");
    privateMethod.setAccessible(true);

    // When
    boolean result = (boolean) privateMethod.invoke(healthChecker);

    // Then
    assertTrue(result, "Termination should be incomplete");
  }
}
