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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseStoreTest {

  private File tempFile;
  private WriteAheadLog wal;
  private DatabaseStore store;

  @BeforeEach
  void setUp() throws IOException {
    tempFile = File.createTempFile("db_store_test", ".log");
    wal = new WriteAheadLog(tempFile);
    store = new DatabaseStore(wal);
  }

  @AfterEach
  void tearDown() {
    if (tempFile != null && tempFile.exists()) {
      tempFile.delete();
    }
  }

  @Test
  void testPutAndGet() throws IOException {
    store.put("key1", "val1");
    assertEquals("val1", store.get("key1"));
  }

  @Test
  void testDelete() throws IOException {
    store.put("key1", "val1");
    assertEquals("val1", store.get("key1"));

    store.delete("key1");
    assertNull(store.get("key1"));
  }

  @Test
  void testSimulateCrashAndRecovery() throws IOException {
    store.put("key1", "val1");
    store.put("key2", "val2");
    store.put("key1", "val1_updated");
    store.delete("key2");
    store.checkpoint();

    Map<String, String> beforeCrashSnapshot = store.getMemTableSnapshot();
    assertEquals("val1_updated", beforeCrashSnapshot.get("key1"));
    assertNull(beforeCrashSnapshot.get("key2"));

    store.simulateCrash();
    assertNull(store.get("key1"));
    assertNull(store.get("key2"));
    assertTrue(store.getMemTableSnapshot().isEmpty());

    store.recover();
    Map<String, String> recoveredSnapshot = store.getMemTableSnapshot();
    assertEquals("val1_updated", recoveredSnapshot.get("key1"));
    assertNull(recoveredSnapshot.get("key2"));
  }
}
