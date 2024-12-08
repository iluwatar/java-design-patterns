package com.iluwatar.logaggregation;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * Collects and buffers logs from different services, periodically flushing them
 * to a central log store based on a time interval or buffer threshold.
 */
@Slf4j
public class LogAggregator {

  private static final int BUFFER_THRESHOLD = 3;
  private static final int FLUSH_INTERVAL = 5; // Interval in seconds for periodic flushing

  private final CentralLogStore centralLogStore;
  private final ConcurrentLinkedQueue<LogEntry> buffer = new ConcurrentLinkedQueue<>();
  private final LogLevel minLogLevel;
  private final AtomicInteger logCount = new AtomicInteger(0);

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  /**
   * Constructor of LogAggregator.
   *
   * @param centralLogStore central log store implementation
   * @param minLogLevel     minimum log level to store log
   */
  public LogAggregator(CentralLogStore centralLogStore, LogLevel minLogLevel) {
    this.centralLogStore = centralLogStore;
    this.minLogLevel = minLogLevel;
    startBufferFlusher();
  }

  /**
   * Collects a log entry and buffers it for eventual flushing to the central log store.
   * Filters logs based on the configured minimum log level.
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
   * @throws InterruptedException If interrupted while shutting down.
   */
  public void stop() throws InterruptedException {
    LOGGER.info("Stopping log aggregator...");
    scheduler.shutdown();
    if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
      LOGGER.error("Log aggregator did not terminate cleanly.");
    }
    flushBuffer();
  }

  /**
   * Flushes the buffered logs to the central log store.
   */
  private void flushBuffer() {
    LOGGER.info("Flushing buffer...");
    LogEntry logEntry;
    while ((logEntry = buffer.poll()) != null) {
      centralLogStore.storeLog(logEntry);
      logCount.decrementAndGet();
    }
    LOGGER.info("Buffer flushed.");
  }

  /**
   * Starts the periodic buffer flusher task using a scheduled executor service.
   */
  private void startBufferFlusher() {
    scheduler.scheduleAtFixedRate(() -> {
      try {
        LOGGER.info("Periodic buffer flush initiated...");
        flushBuffer();
      } catch (Exception e) {
        LOGGER.error("Error during buffer flush", e);
      }
    }, FLUSH_INTERVAL, FLUSH_INTERVAL, TimeUnit.SECONDS);
  }
}
