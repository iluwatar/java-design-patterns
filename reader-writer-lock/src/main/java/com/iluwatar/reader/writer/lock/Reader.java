/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;

/**
 * Reader class, read when it acquired the read lock.
 */
@Slf4j
public class Reader implements Runnable {

  private final Lock readLock;

  private final String name;

  private final long readingTime;

  /**
   * Create new Reader.
   *
   * @param name        - Name of the thread owning the reader
   * @param readLock    - Lock for this reader
   * @param readingTime - amount of time (in milliseconds) for this reader to engage reading
   */
  public Reader(String name, Lock readLock, long readingTime) {
    this.name = name;
    this.readLock = readLock;
    this.readingTime = readingTime;
  }

  /**
   * Create new Reader who reads for 250ms.
   *
   * @param name     - Name of the thread owning the reader
   * @param readLock - Lock for this reader
   */
  public Reader(String name, Lock readLock) {
    this(name, readLock, 250L);
  }

  @Override
  public void run() {
    readLock.lock();
    try {
      read();
    } catch (InterruptedException e) {
      LOGGER.info("InterruptedException when reading", e);
      Thread.currentThread().interrupt();
    } finally {
      readLock.unlock();
    }
  }

  /**
   * Simulate the read operation.
   */
  public void read() throws InterruptedException {
    LOGGER.info("{} begin", name);
    Thread.sleep(readingTime);
    LOGGER.info("{} finish after reading {}ms", name, readingTime);
  }
}
