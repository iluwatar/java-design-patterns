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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteAheadLogTest {

  private File tempFile;
  private WriteAheadLog wal;

  @BeforeEach
  void setUp() throws IOException {
    tempFile = File.createTempFile("wal_test", ".log");
    wal = new WriteAheadLog(tempFile);
  }

  @AfterEach
  void tearDown() {
    if (tempFile != null && tempFile.exists()) {
      if (tempFile.isDirectory()) {
        File[] files = tempFile.listFiles();
        if (files != null) {
          for (File f : files) {
            f.delete();
          }
        }
      }
      tempFile.delete();
    }
  }

  @Test
  void testAppendAndReadAll() throws IOException {
    LogEntry e1 = wal.append(OperationType.SET, "k1", "v1");
    LogEntry e2 = wal.append(OperationType.SET, "k2", "v2");
    LogEntry e3 = wal.append(OperationType.DELETE, "k1", null);

    assertEquals(1, e1.getSequenceNumber());
    assertEquals(2, e2.getSequenceNumber());
    assertEquals(3, e3.getSequenceNumber());

    List<LogEntry> entries = wal.readAll();
    assertEquals(3, entries.size());
    assertEquals(e1, entries.get(0));
    assertEquals(e2, entries.get(1));
    assertEquals(e3, entries.get(2));
  }

  @Test
  void testSequenceNumberResumptionOnReopen() throws IOException {
    wal.append(OperationType.SET, "k1", "v1");
    wal.append(OperationType.SET, "k2", "v2");

    WriteAheadLog reopenedWal = new WriteAheadLog(tempFile);
    LogEntry newEntry = reopenedWal.append(OperationType.SET, "k3", "v3");

    assertEquals(3, newEntry.getSequenceNumber());
  }

  @Test
  void testClear() throws IOException {
    wal.append(OperationType.SET, "k1", "v1");
    assertTrue(tempFile.exists());

    wal.clear();
    assertFalse(tempFile.exists());

    List<LogEntry> entries = wal.readAll();
    assertNotNull(entries);
    assertTrue(entries.isEmpty());
  }

  @Test
  void testReadAllIOExceptionHandling() throws IOException {
    File dir = Files.createTempDirectory("wal_dir_test").toFile();
    WriteAheadLog dirWal = new WriteAheadLog(dir);

    List<LogEntry> entries = dirWal.readAll();
    assertNotNull(entries);
    assertTrue(entries.isEmpty());

    dir.delete();
  }

  @Test
  void testClearIOExceptionHandling() throws IOException {
    File dir = Files.createTempDirectory("wal_nonempty_dir_test").toFile();
    File child = new File(dir, "child.txt");
    child.createNewFile();

    WriteAheadLog dirWal = new WriteAheadLog(dir);
    dirWal.clear();

    assertTrue(dir.exists());

    child.delete();
    dir.delete();
  }
}
