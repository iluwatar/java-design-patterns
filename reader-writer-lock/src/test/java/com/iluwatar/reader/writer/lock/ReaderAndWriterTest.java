/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.reader.writer.lock;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.reader.writer.lock.utils.InMemoryAppender;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongshuwei@gmail.com
 */
public class ReaderAndWriterTest {

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderAndWriterTest.class);

  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testReadAndWrite() throws Exception {

    var lock = new ReaderWriterLock();

    var reader1 = new Reader("Reader 1", lock.readLock());
    var writer1 = new Writer("Writer 1", lock.writeLock());

    var executeService = Executors.newFixedThreadPool(2);
    executeService.submit(reader1);
    // Let reader1 execute first
    Thread.sleep(150);
    executeService.submit(writer1);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown", e);
    }

    assertTrue(appender.logContains("Reader 1 begin"));
    assertTrue(appender.logContains("Reader 1 finish"));
    assertTrue(appender.logContains("Writer 1 begin"));
    assertTrue(appender.logContains("Writer 1 finish"));
  }

  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testWriteAndRead() throws Exception {

    var executeService = Executors.newFixedThreadPool(2);
    var lock = new ReaderWriterLock();

    var reader1 = new Reader("Reader 1", lock.readLock());
    var writer1 = new Writer("Writer 1", lock.writeLock());

    executeService.submit(writer1);
    // Let writer1 execute first
    Thread.sleep(150);
    executeService.submit(reader1);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown", e);
    }

    assertTrue(appender.logContains("Writer 1 begin"));
    assertTrue(appender.logContains("Writer 1 finish"));
    assertTrue(appender.logContains("Reader 1 begin"));
    assertTrue(appender.logContains("Reader 1 finish"));
  }
}

