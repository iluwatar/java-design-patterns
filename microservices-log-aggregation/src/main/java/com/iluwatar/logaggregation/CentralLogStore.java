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
import lombok.extern.slf4j.Slf4j;

/**
 * A centralized store for logs. It collects logs from various services and stores them.
 * This class is thread-safe, ensuring that logs from different services are safely stored
 * concurrently without data races.
 */
@Slf4j
public class CentralLogStore {

  private final ConcurrentLinkedQueue<LogEntry> logs = new ConcurrentLinkedQueue<>();

  /**
   * Stores the given log entry into the central log store.
   *
   * @param logEntry The log entry to store.
   */
  public void storeLog(LogEntry logEntry) {
    if (logEntry == null) {
      LOGGER.error("Received null log entry. Skipping.");
      return;
    }
    logs.offer(logEntry);
  }

  /**
   * Displays all logs currently stored in the central log store.
   */
  public void displayLogs() {
    LOGGER.info("----- Centralized Logs -----");
    for (LogEntry logEntry : logs) {
      LOGGER.info(
          logEntry.getTimestamp() + " [" + logEntry.getLevel() + "] " + logEntry.getMessage());
    }
  }
}
