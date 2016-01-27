package com.iluwatar.reader.writer.lock;

import java.util.concurrent.locks.Lock;

/**
 * Writer class, write when it acquired the write lock
 */
public class Writer implements Runnable {

  private Lock writeLock = null;

  private String name;

  public Writer(String name, Lock writeLock) {
    this.name = name;
    this.writeLock = writeLock;
  }


  @Override
  public void run() {
    writeLock.lock();
    try {
      write();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * Simulate the write operation
   */
  public void write() throws InterruptedException {
    System.out.println(name + " begin");
    Thread.sleep(250);
    System.out.println(name + " finish");
  }
}
