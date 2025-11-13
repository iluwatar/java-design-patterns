/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.singleton.LoggerService.LogLevel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * LoggerService 테스트.
 */
class LoggerServiceTest {

  @BeforeEach
  void setUp() {
    // 각 테스트 전에 로그 히스토리 초기화
    LoggerService.getInstance().clearLogs();
    LoggerService.getInstance().setLogLevel(LogLevel.INFO);
  }

  @Test
  void testSingletonInstance() {
    // Given & When
    LoggerService instance1 = LoggerService.getInstance();
    LoggerService instance2 = LoggerService.getInstance();

    // Then
    assertNotNull(instance1);
    assertNotNull(instance2);
    assertSame(instance1, instance2, "두 인스턴스는 동일해야 함");
  }

  @Test
  void testInfoLogging() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    String message = "Test info message";

    // When
    logger.info(message);

    // Then
    List<String> logs = logger.getLogHistory();
    assertEquals(2, logs.size()); // clearLogs에서 1개 + info 1개
    assertTrue(logs.get(1).contains("[INFO]"));
    assertTrue(logs.get(1).contains(message));
  }

  @Test
  void testErrorLogging() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    String message = "Test error message";

    // When
    logger.error(message);

    // Then
    List<String> logs = logger.getLogHistory();
    assertEquals(2, logs.size()); // clearLogs에서 1개 + error 1개
    assertTrue(logs.get(1).contains("[ERROR]"));
    assertTrue(logs.get(1).contains(message));
  }

  @Test
  void testWarningLogging() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    String message = "Test warning message";

    // When
    logger.warning(message);

    // Then
    List<String> logs = logger.getLogHistory();
    assertEquals(2, logs.size()); // clearLogs에서 1개 + warning 1개
    assertTrue(logs.get(1).contains("[WARNING]"));
    assertTrue(logs.get(1).contains(message));
  }

  @Test
  void testDebugLogging() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    logger.setLogLevel(LogLevel.DEBUG);
    String message = "Test debug message";

    // When
    logger.debug(message);

    // Then
    List<String> logs = logger.getLogHistory();
    assertTrue(logs.size() >= 2); // clearLogs + setLogLevel + debug
    String lastLog = logs.get(logs.size() - 1);
    assertTrue(lastLog.contains("[DEBUG]"));
    assertTrue(lastLog.contains(message));
  }

  @Test
  void testDebugLoggingNotShownWhenLogLevelIsInfo() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    logger.setLogLevel(LogLevel.INFO);
    int initialCount = logger.getLogCount();

    // When
    logger.debug("This should not be logged");

    // Then
    assertEquals(initialCount, logger.getLogCount(), "DEBUG 로그는 INFO 레벨에서 기록되지 않아야 함");
  }

  @Test
  void testErrorLoggingWithException() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    String message = "Error occurred";
    Exception exception = new RuntimeException("Test exception");

    // When
    logger.error(message, exception);

    // Then
    List<String> logs = logger.getLogHistory();
    String lastLog = logs.get(logs.size() - 1);
    assertTrue(lastLog.contains("[ERROR]"));
    assertTrue(lastLog.contains(message));
    assertTrue(lastLog.contains("RuntimeException"));
    assertTrue(lastLog.contains("Test exception"));
  }

  @Test
  void testLogLevelFiltering() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    logger.setLogLevel(LogLevel.WARNING);

    // When
    logger.debug("Debug message"); // 기록되지 않음
    logger.info("Info message");   // 기록되지 않음
    logger.warning("Warning message"); // 기록됨
    logger.error("Error message");     // 기록됨

    // Then
    List<String> logs = logger.getLogHistory();
    // clearLogs (INFO) + setLogLevel (INFO) + warning + error = 4
    assertTrue(logs.size() >= 2);

    List<String> warningLogs = logger.getLogsByLevel(LogLevel.WARNING);
    List<String> errorLogs = logger.getLogsByLevel(LogLevel.ERROR);

    assertFalse(warningLogs.isEmpty());
    assertFalse(errorLogs.isEmpty());
  }

  @Test
  void testGetLogsByLevel() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    logger.setLogLevel(LogLevel.DEBUG);

    // When
    logger.debug("Debug 1");
    logger.info("Info 1");
    logger.warning("Warning 1");
    logger.error("Error 1");
    logger.debug("Debug 2");
    logger.info("Info 2");

    // Then
    List<String> debugLogs = logger.getLogsByLevel(LogLevel.DEBUG);
    List<String> infoLogs = logger.getLogsByLevel(LogLevel.INFO);
    List<String> warningLogs = logger.getLogsByLevel(LogLevel.WARNING);
    List<String> errorLogs = logger.getLogsByLevel(LogLevel.ERROR);

    assertTrue(debugLogs.size() >= 2);
    assertTrue(infoLogs.size() >= 4); // clearLogs + setLogLevel + Info 1 + Info 2
    assertEquals(1, warningLogs.size());
    assertEquals(1, errorLogs.size());
  }

  @Test
  void testClearLogs() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    logger.info("Message 1");
    logger.info("Message 2");
    logger.info("Message 3");

    // When
    logger.clearLogs();

    // Then
    assertEquals(1, logger.getLogCount()); // clearLogs 자체가 로그를 생성
    assertTrue(logger.getLogHistory().get(0).contains("Log history cleared"));
  }

  @Test
  void testLogCount() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    int initialCount = logger.getLogCount();

    // When
    logger.info("Message 1");
    logger.error("Message 2");
    logger.warning("Message 3");

    // Then
    assertEquals(initialCount + 3, logger.getLogCount());
  }

  @Test
  void testSetAndGetLogLevel() {
    // Given
    LoggerService logger = LoggerService.getInstance();

    // When
    logger.setLogLevel(LogLevel.ERROR);

    // Then
    assertEquals(LogLevel.ERROR, logger.getLogLevel());
  }

  @Test
  void testThreadSafety() throws InterruptedException {
    // Given
    LoggerService logger = LoggerService.getInstance();
    int threadCount = 10;
    int messagesPerThread = 50;
    Thread[] threads = new Thread[threadCount];

    // When
    for (int i = 0; i < threadCount; i++) {
      final int threadId = i;
      threads[i] = new Thread(() -> {
        for (int j = 0; j < messagesPerThread; j++) {
          logger.info("Thread " + threadId + " - Message " + j);
        }
      });
      threads[i].start();
    }

    // Wait for all threads to complete
    for (Thread thread : threads) {
      thread.join();
    }

    // Then
    List<String> logs = logger.getLogHistory();
    // clearLogs (1) + setLogLevel (1) + threadCount * messagesPerThread
    int expectedMinimum = threadCount * messagesPerThread;
    assertTrue(logs.size() >= expectedMinimum,
        "Expected at least " + expectedMinimum + " logs but got " + logs.size());
  }

  @Test
  void testLogFormat() {
    // Given
    LoggerService logger = LoggerService.getInstance();
    String message = "Test message";

    // When
    logger.info(message);

    // Then
    List<String> logs = logger.getLogHistory();
    String lastLog = logs.get(logs.size() - 1);

    // 로그 형식: [timestamp] [level] message
    assertTrue(lastLog.matches("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\] \\[INFO\\] " + message));
  }
}
