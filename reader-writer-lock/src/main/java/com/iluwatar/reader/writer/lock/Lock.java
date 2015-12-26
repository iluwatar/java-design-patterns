package com.iluwatar.reader.writer.lock;

/**
 * Lock interface
 */
public interface Lock {

  /**
   * Try to lock, it will wait until get the lock
   */
  public void lock();

  /**
   * Release lock
   */
  public void unlock();
}

