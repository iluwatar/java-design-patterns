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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Storage engine demonstrating Write-Ahead Log (WAL) pattern. All mutations are logged and flushed
 * to persistent WAL storage before modifying the in-memory MemTable.
 */
@Slf4j
public class DatabaseStore {

  @Getter private final WriteAheadLog wal;
  private final Map<String, String> memTable = new HashMap<>();

  /**
   * Constructs DatabaseStore with the specified WriteAheadLog.
   *
   * @param wal persistent write-ahead log manager
   */
  public DatabaseStore(WriteAheadLog wal) {
    this.wal = wal;
  }

  /**
   * Stores a key-value pair. First writes to WAL on disk, then updates the in-memory MemTable.
   *
   * @param key target entry key
   * @param value target entry value
   * @throws IOException if writing to WAL fails
   */
  public synchronized void put(String key, String value) throws IOException {
    wal.append(OperationType.SET, key, value);
    memTable.put(key, value);
    LOGGER.info("Applied SET operation to MemTable: {} = {}", key, value);
  }

  /**
   * Removes a key-value pair. First writes DELETE operation to WAL on disk, then updates the
   * in-memory MemTable.
   *
   * @param key target entry key to delete
   * @throws IOException if writing to WAL fails
   */
  public synchronized void delete(String key) throws IOException {
    wal.append(OperationType.DELETE, key, null);
    memTable.remove(key);
    LOGGER.info("Applied DELETE operation to MemTable: {}", key);
  }

  /**
   * Retrieves value associated with key from the in-memory MemTable.
   *
   * @param key key to lookup
   * @return value or null if non-existent
   */
  public synchronized String get(String key) {
    return memTable.get(key);
  }

  /**
   * Returns an unmodifiable view of current in-memory MemTable state.
   *
   * @return unmodifiable map of stored data
   */
  public synchronized Map<String, String> getMemTableSnapshot() {
    return Collections.unmodifiableMap(new HashMap<>(memTable));
  }

  /**
   * Writes a CHECKPOINT log record.
   *
   * @throws IOException if writing to WAL fails
   */
  public synchronized void checkpoint() throws IOException {
    wal.append(OperationType.CHECKPOINT, null, null);
    LOGGER.info("Checkpoint written to WAL.");
  }

  /** Simulates a system crash or power outage where in-memory state is wiped. */
  public synchronized void simulateCrash() {
    memTable.clear();
    LOGGER.info("!!! SIMULATED SYSTEM CRASH: In-memory MemTable has been wiped !!!");
  }

  /** Replays log entries from persistent Write-Ahead Log to fully recover in-memory state. */
  public synchronized void recover() {
    LOGGER.info("Starting recovery process from WAL...");
    memTable.clear();
    List<LogEntry> entries = wal.readAll();

    for (LogEntry entry : entries) {
      if (entry.getType() == OperationType.SET) {
        memTable.put(entry.getKey(), entry.getValue());
      } else if (entry.getType() == OperationType.DELETE) {
        memTable.remove(entry.getKey());
      }
    }
    LOGGER.info("Recovery completed. Replayed {} log entries into MemTable.", entries.size());
  }
}
