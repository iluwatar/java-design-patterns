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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Manages sequential append-only logging to persistent disk storage. Ensures state changes are
 * flushed to stable storage before in-memory updates.
 */
@Slf4j
public class WriteAheadLog {

  @Getter private final File logFile;
  private final AtomicLong sequenceNumberCounter = new AtomicLong(0);

  /**
   * Initializes WriteAheadLog with target log file. If log file exists, calculates the initial
   * sequence number from existing entries.
   *
   * @param logFile file to use for append-only log entries
   */
  public WriteAheadLog(File logFile) {
    this.logFile = logFile;
    initSequenceNumber();
  }

  private void initSequenceNumber() {
    if (logFile.exists()) {
      List<LogEntry> existingEntries = readAll();
      if (!existingEntries.isEmpty()) {
        long maxSeq = existingEntries.get(existingEntries.size() - 1).getSequenceNumber();
        sequenceNumberCounter.set(maxSeq);
      }
    }
  }

  /**
   * Appends an entry to the log file and flushes to ensure persistence.
   *
   * @param type operation type (SET, DELETE, CHECKPOINT)
   * @param key operation target key
   * @param value operation target value
   * @return recorded LogEntry
   * @throws IOException if writing to persistent storage fails
   */
  public synchronized LogEntry append(OperationType type, String key, String value)
      throws IOException {
    long nextSeq = sequenceNumberCounter.incrementAndGet();
    LogEntry entry = new LogEntry(nextSeq, type, key, value);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
      writer.write(entry.toLogString());
      writer.newLine();
      writer.flush();
    }
    LOGGER.info("WAL Entry appended & flushed to disk: {}", entry);
    return entry;
  }

  /**
   * Reads all log entries sequentially from the persistent log file.
   *
   * @return list of parsed LogEntries in sequential order
   */
  public synchronized List<LogEntry> readAll() {
    List<LogEntry> entries = new ArrayList<>();
    if (!logFile.exists()) {
      return entries;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.isBlank()) {
          entries.add(LogEntry.fromLogString(line));
        }
      }
    } catch (IOException e) {
      LOGGER.error("Failed to read log entries from WAL file: {}", logFile.getAbsolutePath(), e);
    }
    return entries;
  }

  /**
   * Clears the log file and resets the sequence number counter. Typically invoked after a
   * successful checkpoint.
   */
  public synchronized void clear() {
    if (logFile.exists()) {
      try {
        Files.delete(logFile.toPath());
        sequenceNumberCounter.set(0);
        LOGGER.info("WAL log cleared successfully.");
      } catch (IOException e) {
        LOGGER.error("Failed to clear WAL file: {}", logFile.getAbsolutePath(), e);
      }
    }
  }
}
