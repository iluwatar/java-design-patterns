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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LogEntryTest {

  @Test
  void testToLogStringAndFromLogString() {
    LogEntry entry = new LogEntry(1, OperationType.SET, "key1", "val1");
    String logString = entry.toLogString();
    assertEquals("1|SET|key1|val1", logString);

    LogEntry parsed = LogEntry.fromLogString(logString);
    assertEquals(entry, parsed);
    assertEquals(1, parsed.getSequenceNumber());
    assertEquals(OperationType.SET, parsed.getType());
    assertEquals("key1", parsed.getKey());
    assertEquals("val1", parsed.getValue());
  }

  @Test
  void testDeleteLogEntrySerialization() {
    LogEntry entry = new LogEntry(2, OperationType.DELETE, "key2", null);
    String logString = entry.toLogString();
    assertEquals("2|DELETE|key2|", logString);

    LogEntry parsed = LogEntry.fromLogString(logString);
    assertEquals(entry, parsed);
    assertEquals("key2", parsed.getKey());
    assertNull(parsed.getValue());
  }

  @Test
  void testInvalidLogStringThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> LogEntry.fromLogString(null));
    assertThrows(IllegalArgumentException.class, () -> LogEntry.fromLogString("   "));
    assertThrows(IllegalArgumentException.class, () -> LogEntry.fromLogString("1|SET|key1"));
  }

  @Test
  void testEqualsAndHashCode() {
    LogEntry entry1 = new LogEntry(1, OperationType.SET, "k", "v");
    LogEntry entry2 = new LogEntry(1, OperationType.SET, "k", "v");
    LogEntry entry3 = new LogEntry(2, OperationType.SET, "k", "v");

    assertEquals(entry1, entry2);
    assertEquals(entry1.hashCode(), entry2.hashCode());
    assertNotEquals(entry1, entry3);
  }
}
