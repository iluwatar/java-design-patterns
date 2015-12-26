package com.iluwatar.reader.writer.lock;

import java.util.HashSet;
import java.util.Set;

/**
 * Class responsible for control the access for reader or writer
 */
public class ReaderWriterLock {

  /**
   * Mutex for reader
   */
  private Object r = new Object();

  /**
   * Global mutex for reader or writer, use to save the holding object
   */
  private Set<Object> g = new HashSet<>();

  /**
   * Current reader count
   */
  private int readerCount = 0;

  private ReaderLock readLock = new ReaderLock();
  private WriterLock writeLock = new WriterLock();


  public Lock readLock() {
    return readLock;
  }


  public Lock writeLock() {
    return writeLock;
  }


  /**
   * Reader Lock, can be access for more than one reader concurrently if no writer get the lock
   */
  private class ReaderLock implements Lock {

    @Override
    public void lock() {

      synchronized (r) {

        readerCount++;
        if (readerCount == 1) {

          synchronized (g) {

            while (true) {
              if (isLockFree() || isReaderOwnThisLock()) {
                g.add(this);
                break;
              } else {
                waitUninterruptely(g);
              }
            }
          }

        }
      }
    }


    @Override
    public void unlock() {

      synchronized (r) {
        readerCount--;
        if (readerCount == 0) {
          synchronized (g) {
            g.remove(this);
            g.notifyAll();
          }
        }
      }

    }

  }


  /**
   * Writer Lock, can only be accessed by one writer concurrently
   */
  private class WriterLock implements Lock {

    @Override
    public void lock() {

      synchronized (g) {

        while (true) {

          if (isLockFree()) {
            g.add(this);
            break;
          } else if (isWriterOwnThisLock()) {
            waitUninterruptely(g);
          } else if (isReaderOwnThisLock()) {
            waitUninterruptely(g);
          } else {
            throw new RuntimeException("it should never reach here");
          }
        }
      }
    }


    @Override
    public void unlock() {

      synchronized (g) {
        g.remove(this);
        g.notifyAll();
      }
    }
  }

  private boolean isWriterOwnThisLock() {
    return g.contains(writeLock);
  }

  private boolean isReaderOwnThisLock() {
    return g.contains(readLock);
  }

  private boolean isLockFree() {
    return g.isEmpty();
  }



  private void waitUninterruptely(Object o) {
    try {
      o.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

