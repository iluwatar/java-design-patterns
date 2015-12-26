package com.iluwatar.reader.writer.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Reader writer lock is a synchronization primitive that solves one of the readers–writers
 * problems. An RW lock allows concurrent access for read-only operations, while write operations
 * require exclusive access.
 * <p>
 * Below example use two mutexes to demonstrate the concurrent access of mutilple readers and
 * writers.
 * 
 */
public class App {

  private static Random ran = new Random();

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {

    ExecutorService es = Executors.newFixedThreadPool(1000);
    ReaderWriterLock lock = new ReaderWriterLock();

    AtomicInteger index = new AtomicInteger(0);
    IntStream.range(0, 100).forEach(i -> {
      Runnable task = null;
      if (ran.nextFloat() <= 0.6) {
        task = new Runnable() {
          @Override
          public void run() {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            try {
              int cur = index.getAndIncrement();
              System.out.println("Writer " + cur + " begin");
              simulateReadOrWrite();
              System.out.println("Writer " + cur + " finish");
            } finally {
              writeLock.unlock();
            }
          }
        };
      } else {
        task = new Runnable() {

          @Override
          public void run() {
            Lock readLock = lock.readLock();
            readLock.lock();
            try {
              int cur = index.getAndIncrement();
              System.out.println("Reader " + cur + " begin");
              simulateReadOrWrite();
              System.out.println("Reader " + cur + " finish");

            } finally {
              readLock.unlock();
            }
          }
        };
      }
      es.submit(task);
    });

  }

  private static void simulateReadOrWrite() {
    try {
      Thread.sleep((long) (ran.nextFloat() * 10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
