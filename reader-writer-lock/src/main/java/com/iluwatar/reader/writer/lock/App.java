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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In a multiple thread applications, the threads may try to synchronize the shared resources
 * regardless of read or write operation. It leads to a low performance especially in a "read more
 * write less" system as indeed the read operations are thread-safe to another read operation.
 *
 * <p>Reader writer lock is a synchronization primitive that try to resolve this problem. This
 * pattern allows concurrent access for read-only operations, while write operations require
 * exclusive access. This means that multiple threads can read the data in parallel but an exclusive
 * lock is needed for writing or modifying data. When a writer is writing the data, all other
 * writers or readers will be blocked until the writer is finished writing.
 *
 * <p>This example use two mutex to demonstrate the concurrent access of multiple readers and
 * writers.
 *
 * @author hongshuwei@gmail.com
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    ExecutorService executeService = Executors.newFixedThreadPool(10);
    ReaderWriterLock lock = new ReaderWriterLock();

    // Start writers
    IntStream.range(0, 5)
        .forEach(i -> executeService.submit(new Writer("Writer " + i, lock.writeLock(),
            ThreadLocalRandom.current().nextLong(5000))));
    LOGGER.info("Writers added...");

    // Start readers
    IntStream.range(0, 5)
        .forEach(i -> executeService.submit(new Reader("Reader " + i, lock.readLock(),
            ThreadLocalRandom.current().nextLong(10))));
    LOGGER.info("Readers added...");

    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      LOGGER.error("Error sleeping before adding more readers", e);
      Thread.currentThread().interrupt();
    }

    // Start readers
    IntStream.range(6, 10)
        .forEach(i -> executeService.submit(new Reader("Reader " + i, lock.readLock(),
            ThreadLocalRandom.current().nextLong(10))));
    LOGGER.info("More readers added...");


    // In the system console, it can see that the read operations are executed concurrently while
    // write operations are exclusive.
    executeService.shutdown();
    try {
      executeService.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown", e);
      Thread.currentThread().interrupt();
    }

  }

}
