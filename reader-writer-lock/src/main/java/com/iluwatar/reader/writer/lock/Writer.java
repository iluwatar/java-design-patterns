/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writer class, write when it acquired the write lock
 */
public class Writer implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(Writer.class);

  private Lock writeLock;

  private String name;
  
  private long writingTime;

  /**
   * Create new Writer who writes for 250ms
   * 
   * @param name - Name of the thread owning the writer
   * @param writeLock - Lock for this writer
   */
  public Writer(String name, Lock writeLock) {
    this(name, writeLock, 250L);
  }
  
  /**
   * Create new Writer
   * 
   * @param name - Name of the thread owning the writer
   * @param writeLock - Lock for this writer
   * @param writingTime - amount of time (in milliseconds) for this reader to engage writing
   */
  public Writer(String name, Lock writeLock, long writingTime) {
    this.name = name;
    this.writeLock = writeLock;
    this.writingTime = writingTime;
  }


  @Override
  public void run() {
    writeLock.lock();
    try {
      write();
    } catch (InterruptedException e) {
      LOGGER.info("InterruptedException when writing", e);
      Thread.currentThread().interrupt();
    } finally {
      writeLock.unlock();
    }
  }
  
  /**
   * Simulate the write operation
   */
  public void write() throws InterruptedException {
    LOGGER.info("{} begin", name);
    Thread.sleep(writingTime);
    LOGGER.info("{} finished after writing {}ms", name, writingTime);
  }
}
