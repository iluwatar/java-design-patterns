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

import com.iluwatar.reader.writer.lock.utils.InMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

/**
 * @author hongshuwei@gmail.com
 */
public class WriterTest {

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender(Writer.class);
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(WriterTest.class);

  /**
   * Verify that multiple writers will get the lock in order.
   */
  @Test
  public void testWrite() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Writer writer1 = spy(new Writer("Writer 1", lock.writeLock()));
    Writer writer2 = spy(new Writer("Writer 2", lock.writeLock()));

    executeService.submit(writer1);
    // Let write1 execute first
    Thread.sleep(150);
    executeService.submit(writer2);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown", e);
    }
    // Write operation will hold the write lock 250 milliseconds, so here we verify that when two
    // writer execute concurrently, the second writer can only writes only when the first one is
    // finished.
    assertTrue(appender.logContains("Writer 1 begin"));
    assertTrue(appender.logContains("Writer 1 finish"));
    assertTrue(appender.logContains("Writer 2 begin"));
    assertTrue(appender.logContains("Writer 2 finish"));
  }
}
