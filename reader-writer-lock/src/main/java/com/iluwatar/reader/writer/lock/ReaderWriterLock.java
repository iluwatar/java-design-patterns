/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

/**
 * Class responsible for control the access for reader or writer
 * 
 * Allows multiple readers to hold the lock at same time, but if any writer holds the lock then
 * readers wait. If reader holds the lock then writer waits. This lock is not fair.
 */
public class ReaderWriterLock implements ReadWriteLock {


  private Object readerMutex = new Object();

  private int currentReaderCount;

  /**
   * Global mutex is used to indicate that whether reader or writer gets the lock in the moment.
   * <p>
   * 1. When it contains the reference of {@link readerLock}, it means that the lock is acquired by
   * the reader, another reader can also do the read operation concurrently. <br>
   * 2. When it contains the reference of reference of {@link writerLock}, it means that the lock is
   * acquired by the writer exclusively, no more reader or writer can get the lock.
   * <p>
   * This is the most important field in this class to control the access for reader/writer.
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
   * return true when globalMutex hold the reference of writerLock
   */
  private boolean doesWriterOwnThisLock() {
    return globalMutex.contains(writerLock);
  }

  /**
   * return true when globalMutex hold the reference of readerLock
   */
  private boolean doesReaderOwnThisLock() {
    return globalMutex.contains(readerLock);
  }

  /**
   * Nobody get the lock when globalMutex contains nothing
   * 
   */
  private boolean isLockFree() {
    return globalMutex.isEmpty();
  }

  private static void waitUninterruptibly(Object o) {
    try {
      o.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reader Lock, can be access for more than one reader concurrently if no writer get the lock
   */
  private class ReadLock implements Lock {

    @Override
    public void lock() {

      synchronized (readerMutex) {

        currentReaderCount++;
        if (currentReaderCount == 1) {
          // Try to get the globalMutex lock for the first reader
          synchronized (globalMutex) {
            while (true) {
              // If the no one get the lock or the lock is locked by reader, just set the reference
              // to the globalMutex to indicate that the lock is locked by Reader.
              if (isLockFree() || doesReaderOwnThisLock()) {
                globalMutex.add(this);
                break;
              } else {
                // If lock is acquired by the write, let the thread wait until the writer release
                // the lock
                waitUninterruptibly(globalMutex);
              }
            }
          }

        }
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
   * Writer Lock, can only be accessed by one writer concurrently
   */
  private class WriteLock implements Lock {

    @Override
    public void lock() {

      synchronized (globalMutex) {

        while (true) {
          // When there is no one acquired the lock, just put the writeLock reference to the
          // globalMutex to indicate that the lock is acquired by one writer.
          // It is ensure that writer can only get the lock when no reader/writer acquired the lock.
          if (isLockFree()) {
            globalMutex.add(this);
            break;
          } else if (doesWriterOwnThisLock()) {
            // Wait when other writer get the lock
            waitUninterruptibly(globalMutex);
          } else if (doesReaderOwnThisLock()) {
            // Wait when other reader get the lock
            waitUninterruptibly(globalMutex);
          } else {
            throw new AssertionError("it should never reach here");
          }
        }
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
