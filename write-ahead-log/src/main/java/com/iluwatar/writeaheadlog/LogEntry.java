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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Represents a single record entry in the Write-Ahead Log. */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LogEntry {

  private static final String DELIMITER = "|";

  private final long sequenceNumber;
  private final OperationType type;
  private final String key;
  private final String value;

  /**
   * Serializes the LogEntry to a delimited string format suitable for append-only logging.
   *
   * @return delimited log line representation
   */
  public String toLogString() {
    return sequenceNumber
        + DELIMITER
        + type
        + DELIMITER
        + (key != null ? key : "")
        + DELIMITER
        + (value != null ? value : "");
  }

  /**
   * Deserializes a delimited string line into a LogEntry instance.
   *
   * @param line serialized log line
   * @return parsed LogEntry
   * @throws IllegalArgumentException if the log line format is invalid
   */
  public static LogEntry fromLogString(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("Log line cannot be null or blank");
    }
    String[] parts = line.split("\\|", -1);
    if (parts.length < 4) {
      throw new IllegalArgumentException("Invalid log line format: " + line);
    }
    long sequenceNumber = Long.parseLong(parts[0]);
    OperationType type = OperationType.valueOf(parts[1]);
    String key = parts[2].isEmpty() ? null : parts[2];
    String value = parts[3].isEmpty() ? null : parts[3];
    return new LogEntry(sequenceNumber, type, key, value);
  }
}
