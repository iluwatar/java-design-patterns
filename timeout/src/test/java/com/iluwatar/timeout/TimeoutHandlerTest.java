package com.iluwatar.timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for TimeoutHandler.
 */
class TimeoutHandlerTest {

  private TimeoutHandler handler;

  @BeforeEach
  void setUp() {
    handler = new TimeoutHandler();
  }

  @AfterEach
  void tearDown() {
    handler.shutdown();
  }

  @Test
  void testSuccessfulExecution() throws TimeoutException {
    String result = handler.execute(() -> {
      Thread.sleep(100);
      return "Success";
    }, 1, TimeUnit.SECONDS);

    assertEquals("Success", result);
  }

  @Test
  void testTimeoutException() {
    TimeoutException exception = assertThrows(TimeoutException.class, () -> {
      handler.execute(() -> {
        Thread.sleep(3000);
        return "Should timeout";
      }, 1, TimeUnit.SECONDS);
    });

    assertTrue(exception.getMessage().contains("timed out"));
  }

  @Test
  void testImmediateReturn() throws TimeoutException {
    String result = handler.execute(() -> "Immediate", 1, TimeUnit.SECONDS);
    assertEquals("Immediate", result);
  }

  @Test
  void testTaskThrowsException() {
    TimeoutException exception = assertThrows(TimeoutException.class, () -> {
      handler.execute(() -> {
        throw new RuntimeException("Task failed");
      }, 1, TimeUnit.SECONDS);
    });

    assertTrue(exception.getMessage().contains("Operation failed"));
  }
}