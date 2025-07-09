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
package com.iluwatar.logaggregation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
// CRITICAL MISSING IMPORTS - FIXED!
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * FIXED Championship Test Suite - LogAggregator
 * 
 * Fixed to work with the actual LogAggregator API and proper imports
 */
@ExtendWith(MockitoExtension.class)
class LogAggregatorTest {
  
  @Mock 
  private CentralLogStore centralLogStore;
  
  private LogAggregator logAggregator;

  @BeforeEach
  void setUp() {
    logAggregator = new LogAggregator(centralLogStore, LogLevel.INFO);
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    // 🚀 CHAMPIONSHIP CLEANUP - Properly shutdown the event-driven aggregator
    if (logAggregator != null && logAggregator.isRunning()) {
      logAggregator.stop();
      logAggregator.awaitShutdown();
    }
  }

  @Test
  void whenThreeInfoLogsAreCollected_thenCentralLogStoreShouldStoreAllOfThem() throws InterruptedException {
    // ELITE FIX: Account for asynchronous threshold-based flushing
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 1"));
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 2"));
    
    // At this point, we should have 2 logs in buffer, no flush yet
    assertEquals(2, logAggregator.getLogCount());
    verifyNoInteractionsWithCentralLogStore();
    
    // Third log should trigger immediate flush (threshold = 3)
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 3"));
    
    // CHAMPIONSHIP WAIT: Allow time for ScheduledExecutorService to process
    Thread.sleep(1000); // Give executor time to flush
    
    verifyCentralLogStoreInvokedTimes(3);
    assertEquals(0, logAggregator.getLogCount()); // Buffer should be empty after flush
  }

  @Test
  void whenDebugLogIsCollected_thenNoLogsShouldBeStored() throws InterruptedException {
    logAggregator.collectLog(createLogEntry(LogLevel.DEBUG, "Sample debug log message"));
    
    // Debug log should be filtered out before reaching buffer
    assertEquals(0, logAggregator.getLogCount());
    assertEquals(0, logAggregator.getBufferSize());
    
    // Wait a bit to ensure no delayed processing
    Thread.sleep(500);
    
    verifyNoInteractionsWithCentralLogStore();
  }

  @Test
  void whenTwoLogsCollected_thenBufferShouldContainThem() {
    // 🎯 NEW TEST: Verify buffer state management
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Message 1"));
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Message 2"));
    
    assertEquals(2, logAggregator.getLogCount());
    assertEquals(2, logAggregator.getBufferSize());
    
    // Should not trigger flush yet (threshold is 3)
    verifyNoInteractionsWithCentralLogStore();
  }

  @Test
  void whenScheduledFlushOccurs_thenBufferedLogsShouldBeStored() throws InterruptedException {
    // 🏆 NEW TEST: Verify scheduled periodic flushing
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Scheduled flush test"));
    
    assertEquals(1, logAggregator.getLogCount());
    verifyNoInteractionsWithCentralLogStore();
    
    // Wait for scheduled flush (FLUSH_INTERVAL_SECONDS = 5)
    Thread.sleep(6000); // 5 seconds + buffer
    
    verifyCentralLogStoreInvokedTimes(1);
    assertEquals(0, logAggregator.getLogCount());
  }

  @Test
  void whenLogAggregatorStopped_thenRemainingLogsShouldBeStored() throws InterruptedException {
    // 🚀 NEW TEST: Verify graceful shutdown flushes remaining logs
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Final message 1"));
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Final message 2"));
    
    assertEquals(2, logAggregator.getLogCount());
    verifyNoInteractionsWithCentralLogStore();
    
    // Stop should trigger final flush
    logAggregator.stop();
    logAggregator.awaitShutdown();
    
    verifyCentralLogStoreInvokedTimes(2);
    assertEquals(0, logAggregator.getLogCount());
    assertFalse(logAggregator.isRunning());
  }

  @Test
  void whenLogLevelBelowThreshold_thenLogShouldBeFiltered() {
    //  FIXED TEST: Only use available log levels
    logAggregator.collectLog(createLogEntry(LogLevel.DEBUG, "Debug message"));
    
    assertEquals(0, logAggregator.getLogCount());
    assertEquals(0, logAggregator.getBufferSize());
    verifyNoInteractionsWithCentralLogStore();
  }

  @Test
  void whenLogLevelAtOrAboveThreshold_thenLogShouldBeAccepted() {
    // FIXED TEST: Use only available log levels (INFO, DEBUG, ERROR)
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Info message"));
    logAggregator.collectLog(createLogEntry(LogLevel.ERROR, "Error message"));
    
    assertEquals(2, logAggregator.getLogCount());
    assertEquals(2, logAggregator.getBufferSize());
  }

  @Test
  void whenNullLogLevelProvided_thenLogShouldBeSkipped() {
    //  EDGE CASE TEST: Null safety
    LogEntry nullLevelEntry = new LogEntry("ServiceA", null, "Null level message", LocalDateTime.now());
    
    logAggregator.collectLog(nullLevelEntry);
    
    assertEquals(0, logAggregator.getLogCount());
    verifyNoInteractionsWithCentralLogStore();
  }

  @Test
  void whenLogAggregatorIsShutdown_thenNewLogsShouldBeRejected() throws InterruptedException {
    //  NEW TEST: Verify shutdown behavior
    logAggregator.stop();
    logAggregator.awaitShutdown();
    
    assertFalse(logAggregator.isRunning());
    
    // Try to add log after shutdown
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Post-shutdown message"));
    
    assertEquals(0, logAggregator.getLogCount());
    verifyNoInteractionsWithCentralLogStore();
  }

  @Test
  void testBasicFunctionality() throws InterruptedException {
    // SIMPLIFIED TEST: Basic functionality without advanced features
    assertTrue(logAggregator.isRunning());
    
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Basic test"));
    assertEquals(1, logAggregator.getLogCount());
  }

   private static LogEntry createLogEntry(LogLevel logLevel, String message) {
    return new LogEntry("ServiceA", logLevel, message, LocalDateTime.now());
  }

  private void verifyNoInteractionsWithCentralLogStore() {
    verify(centralLogStore, times(0)).storeLog(any());
  }

  private void verifyCentralLogStoreInvokedTimes(int times) {
    verify(centralLogStore, times(times)).storeLog(any());
  }
}
