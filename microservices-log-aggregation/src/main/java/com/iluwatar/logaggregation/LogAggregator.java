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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
/**
 * Responsible for collecting and buffering logs from different services. Once the logs reach a
 * certain threshold or after a certain time interval, they are flushed to the central log store.
 * This class ensures logs are collected and processed asynchronously and efficiently, providing
 * both an immediate collection and periodic flushing.
 */
@Slf4j
public class LogAggregator {

  private static final int BUFFER_THRESHOLD = 3;
  private static final int FLUSH_INTERVAL_SECONDS = 5;
  private static final int SHUTDOWN_TIMEOUT_SECONDS = 10;
  
  private final CentralLogStore centralLogStore;
  private final BlockingQueue<LogEntry> buffer = new LinkedBlockingQueue<>();
  private final LogLevel minLogLevel;
  private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
  private final AtomicInteger logCount = new AtomicInteger(0);
  private final CountDownLatch shutdownLatch = new CountDownLatch(1);
  private volatile boolean running = true;
  /**
   * constructor of LogAggregator.
   *
   * @param centralLogStore central log store implement
   * @param minLogLevel min log level to store log
   */
  public LogAggregator(CentralLogStore centralLogStore, LogLevel minLogLevel) {
   this.centralLogStore = centralLogStore;
    this.minLogLevel = minLogLevel;
    startPeriodicFlusher();
    
    // Add shutdown hook for graceful termination
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        stop();
      } catch (InterruptedException e) {
        LOGGER.warn("Shutdown interrupted", e);
        Thread.currentThread().interrupt();
      }
    }));
  }

  /**
   * Collects a given log entry, and filters it by the defined log level.
   *
   * @param logEntry The log entry to collect.
   */
   public void collectLog(LogEntry logEntry) {
    if (!running) {
      LOGGER.warn("LogAggregator is shutting down. Skipping log entry.");
      return;
    }
    
    if (logEntry.getLevel() == null || minLogLevel == null) {
      LOGGER.warn("Log level or threshold level is null. Skipping.");
      return;
    }

    if (logEntry.getLevel().compareTo(minLogLevel) < 0) {
      LOGGER.debug("Log level below threshold. Skipping.");
      return;
    }

    // BlockingQueue.offer() is non-blocking and thread-safe
    boolean added = buffer.offer(logEntry);
    if (!added) {
      LOGGER.warn("Failed to add log entry to buffer - queue may be full");
      return;
    }

    // Check if immediate flush is needed due to threshold
    if (logCount.incrementAndGet() >= BUFFER_THRESHOLD) {
      // Schedule immediate flush instead of blocking current thread
      scheduledExecutor.execute(this::flushBuffer);
    }
  }

  /**
   * Stops the log aggregator service and flushes any remaining logs to the central log store.
   *
   * @throws InterruptedException If any thread has interrupted the current thread.
   */
 public void stop() throws InterruptedException {
    LOGGER.info("Stopping LogAggregator...");
    running = false;
    
    // Shutdown the scheduler gracefully
    scheduledExecutor.shutdown();
    
    try {
      // Wait for scheduled tasks to complete
      if (!scheduledExecutor.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        LOGGER.warn("Scheduler did not terminate gracefully, forcing shutdown");
        scheduledExecutor.shutdownNow();
        
        // Wait a bit more for tasks to respond to interruption
        if (!scheduledExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
          LOGGER.error("Scheduler did not terminate after forced shutdown");
        }
      }
    } finally {
      // Final flush of any remaining logs
      flushBuffer();
      shutdownLatch.countDown();
      LOGGER.info("LogAggregator stopped successfully");
    }
  }



  /**
   * Waits for the LogAggregator to complete shutdown.
   * Useful for testing or controlled shutdown scenarios.
   *
   * @throws InterruptedException If any thread has interrupted the current thread.
   */
public void awaitShutdown() throws InterruptedException {
    shutdownLatch.await();
  }


  private void flushBuffer() {
    if (!running && buffer.isEmpty()) {
      return;
    }
    
    try {
      List<LogEntry> batch = new ArrayList<>();
      int drained = 0;
      
      // Drain up to a reasonable batch size for efficiency
      LogEntry logEntry;
      while ((logEntry = buffer.poll()) != null && drained < 100) {
        batch.add(logEntry);
        drained++;
      }
      
      if (!batch.isEmpty()) {
        LOGGER.debug("Flushing {} log entries to central store", batch.size());
        
        // Process the batch
        for (LogEntry entry : batch) {
          centralLogStore.storeLog(entry);
          logCount.decrementAndGet();
        }
        
        LOGGER.debug("Successfully flushed {} log entries", batch.size());
      }
    } catch (Exception e) {
      LOGGER.error("Error occurred while flushing buffer", e);
    }
  }


  /**
   * Starts the periodic buffer flusher using ScheduledExecutorService.
   * This eliminates the busy-waiting loop with Thread.sleep().
   */
   private void startPeriodicFlusher() {
    scheduledExecutor.scheduleAtFixedRate(
        () -> {
          if (running) {
            try {
              flushBuffer();
            } catch (Exception e) {
              LOGGER.error("Error in periodic flush", e);
            }
          }
        },
        FLUSH_INTERVAL_SECONDS,  // Initial delay
        FLUSH_INTERVAL_SECONDS,  // Period
        TimeUnit.SECONDS
    );
    
    LOGGER.info("Periodic log flusher started with interval of {} seconds", FLUSH_INTERVAL_SECONDS);
  }
  /**
   * Gets the current number of buffered log entries.
   * Useful for monitoring and testing.
   *
   * @return Current buffer size
   */
  public int getBufferSize() {
    return buffer.size();
  }

  /**
   * Gets the current log count.
   * Useful for monitoring and testing.
   *
   * @return Current log count
   */
  public int getLogCount() {
    return logCount.get();
  }

  /**
   * Checks if the LogAggregator is currently running.
   *
   * @return true if running, false if stopped or stopping
   */
  public boolean isRunning() {
    return running;
  }
}
