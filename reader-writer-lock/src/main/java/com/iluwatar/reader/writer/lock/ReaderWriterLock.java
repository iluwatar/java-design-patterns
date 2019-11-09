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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for control the access for reader or writer
 *
 * <p>Allows multiple readers to hold the lock at same time, but if any writer holds the lock then
 * readers wait. If reader holds the lock then writer waits. This lock is not fair.
 */
public class ReaderWriterLock implements ReadWriteLock {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderWriterLock.class);


  private Object readerMutex = new Object();

  private int currentReaderCount;

  /**
   * Global mutex is used to indicate that whether reader or writer gets the lock in the moment.
   *
   * <p>1. When it contains the reference of {@link #readerLock}, it means that the lock is
   * acquired by the reader, another reader can also do the read operation concurrently. <br> 2.
   * When it contains the reference of reference of {@link #writerLock}, it means that the lock is
   * acquired by the writer exclusively, no more reader or writer can get the lock.
   *
   * <p>This is the most important field in this class to control the access for reader/writer.
   */
  private Set<Object> globalMutex = new HashSet<>();

  private ReadLock readerLock = new ReadLock();
  private WriteLock writerLock = new WriteLock();

  @Override
  public Lock readLock() {
    return readerLock;
  }

  @Override
  public Lock writeLock() {
    return writerLock;
  }

  /**
   * return true when globalMutex hold the reference of writerLock.
   */
  private boolean doesWriterOwnThisLock() {
    return globalMutex.contains(writerLock);
  }

  /**
   * Nobody get the lock when globalMutex contains nothing.
   */
  private boolean isLockFree() {
    return globalMutex.isEmpty();
  }

  /**
   * Reader Lock, can be access for more than one reader concurrently if no writer get the lock.
   */
  private class ReadLock implements Lock {

    @Override
    public void lock() {
      synchronized (readerMutex) {
        currentReaderCount++;
        if (currentReaderCount == 1) {
          acquireForReaders();
        }
      }
    }

    /**
     * Acquire the globalMutex lock on behalf of current and future concurrent readers. Make sure no
     * writers currently owns the lock.
     */
    private void acquireForReaders() {
      // Try to get the globalMutex lock for the first reader
      synchronized (globalMutex) {
        // If the no one get the lock or the lock is locked by reader, just set the reference
        // to the globalMutex to indicate that the lock is locked by Reader.
        while (doesWriterOwnThisLock()) {
          try {
            globalMutex.wait();
          } catch (InterruptedException e) {
            LOGGER
                .info("InterruptedException while waiting for globalMutex in acquireForReaders", e);
            Thread.currentThread().interrupt();
          }
        }
        globalMutex.add(this);
      }
    }

    @Override
    public void unlock() {

      synchronized (readerMutex) {
        currentReaderCount--;
        // Release the lock only when it is the last reader, it is ensure that the lock is released
        // when all reader is completely.
        if (currentReaderCount == 0) {
          synchronized (globalMutex) {
            // Notify the waiter, mostly the writer
            globalMutex.remove(this);
            globalMutex.notifyAll();
          }
        }
      }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
      throw new UnsupportedOperationException();
    }

  }

  /**
   * Writer Lock, can only be accessed by one writer concurrently.
   */
  private class WriteLock implements Lock {

    @Override
    public void lock() {

      synchronized (globalMutex) {

        // Wait until the lock is free.
        while (!isLockFree()) {
          try {
            globalMutex.wait();
          } catch (InterruptedException e) {
            LOGGER.info("InterruptedException while waiting for globalMutex to begin writing", e);
            Thread.currentThread().interrupt();
          }
        }
        // When the lock is free, acquire it by placing an entry in globalMutex
        globalMutex.add(this);
      }
    }

    @Override
    public void unlock() {

      synchronized (globalMutex) {
        globalMutex.remove(this);
        // Notify the waiter, other writer or reader
        globalMutex.notifyAll();
      }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
      throw new UnsupportedOperationException();
    }
  }

}
