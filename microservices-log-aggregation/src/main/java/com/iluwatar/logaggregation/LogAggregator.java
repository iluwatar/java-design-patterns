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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * Responsible for collecting and buffering logs from different services.
 * Once the logs reach a certain threshold or after a certain time interval,
 * they are flushed to the central log store. This class ensures logs are collected
 * and processed asynchronously and efficiently, providing both an immediate collection
 * and periodic flushing.
 */
@Slf4j
public class LogAggregator {

  private static final int BUFFER_THRESHOLD = 3;
  private final CentralLogStore centralLogStore;
  private final ConcurrentLinkedQueue<LogEntry> buffer = new ConcurrentLinkedQueue<>();
  private final LogLevel minLogLevel;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final AtomicInteger logCount = new AtomicInteger(0);

  /**
   * constructor of LogAggregator.
   *
   * @param centralLogStore central log store implement
   * @param minLogLevel min log level to store log
   */
  public LogAggregator(CentralLogStore centralLogStore, LogLevel minLogLevel) {
    this.centralLogStore = centralLogStore;
    this.minLogLevel = minLogLevel;
    startBufferFlusher();
  }

  /**
   * Collects a given log entry, and filters it by the defined log level.
   *
   * @param logEntry The log entry to collect.
   */
  public void collectLog(LogEntry logEntry) {
    if (logEntry.getLevel() == null || minLogLevel == null) {
      LOGGER.warn("Log level or threshold level is null. Skipping.");
      return;
    }

    if (logEntry.getLevel().compareTo(minLogLevel) < 0) {
      LOGGER.debug("Log level below threshold. Skipping.");
      return;
    }

    buffer.offer(logEntry);

    if (logCount.incrementAndGet() >= BUFFER_THRESHOLD) {
      flushBuffer();
    }
  }

  /**
   * Stops the log aggregator service and flushes any remaining logs to
   * the central log store.
   *
   * @throws InterruptedException If any thread has interrupted the current thread.
   */
  public void stop() throws InterruptedException {
    executorService.shutdownNow();
    if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
      LOGGER.error("Log aggregator did not terminate.");
    }
    flushBuffer();
  }

  private void flushBuffer() {
    LogEntry logEntry;
    while ((logEntry = buffer.poll()) != null) {
      centralLogStore.storeLog(logEntry);
      logCount.decrementAndGet();
    }
  }

  private void startBufferFlusher() {
    executorService.execute(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(5000); // Flush every 5 seconds.
          flushBuffer();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
  }
}
