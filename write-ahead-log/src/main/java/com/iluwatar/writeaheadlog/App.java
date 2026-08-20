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

package com.iluwatar.writeaheadlog;

import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * Main application class demonstrating the Write-Ahead Log (WAL) design pattern.
 *
 * <p>The WAL pattern guarantees durability by ensuring every mutation (SET, DELETE) is written to a
 * persistent append-only log file on disk BEFORE updating in-memory state. If the system crashes
 * unexpectedly, replaying log entries from the WAL file restores state.
 */
@Slf4j
public class App {

  /**
   * Application entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    try {
      File logFile = File.createTempFile("wal_demo", ".log");
      logFile.deleteOnExit();

      LOGGER.info(
          "=== 1. Initializing Storage Engine with WAL at {} ===", logFile.getAbsolutePath());
      WriteAheadLog wal = new WriteAheadLog(logFile);
      DatabaseStore store = new DatabaseStore(wal);

      LOGGER.info("=== 2. Performing Data Operations (Write-Ahead Logging) ===");
      store.put("user:101", "Alice");
      store.put("user:102", "Bob");
      store.put("user:103", "Charlie");
      store.put("user:102", "Bob Smith");
      store.delete("user:103");
      store.checkpoint();

      LOGGER.info("MemTable snapshot before crash: {}", store.getMemTableSnapshot());

      LOGGER.info("=== 3. Simulating Unexpected System Crash ===");
      store.simulateCrash();
      LOGGER.info("MemTable snapshot after crash: {}", store.getMemTableSnapshot());

      LOGGER.info("=== 4. System Restart & Recovery from WAL ===");
      store.recover();
      LOGGER.info("MemTable snapshot post recovery: {}", store.getMemTableSnapshot());

      if (logFile.exists()) {
        logFile.delete();
      }
    } catch (IOException e) {
      LOGGER.error("An error occurred during WAL demonstration: {}", e.getMessage(), e);
    }
  }
}
