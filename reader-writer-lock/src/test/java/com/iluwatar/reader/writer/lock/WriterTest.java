package com.iluwatar.reader.writer.lock;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author hongshuwei@gmail.com
 */
public class WriterTest {

  /**
   * Verify that multiple writers will get the lock in order.
   */
  @Ignore // intermittent failures when executed on CI
  @Test
  public void testWrite() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Writer writer1 = spy(new Writer("Writer 1", lock.writeLock()));
    Writer writer2 = spy(new Writer("Writer 2", lock.writeLock()));

    executeService.submit(writer1);
    // Let write1 execute first
    Thread.sleep(50);
    executeService.submit(writer2);

    // Write operation will hold the write lock 100 milliseconds, so here we verify that when two
    // write excute concurrently
    // 1. The first write will get the lock and and write in 60ms
    // 2. The second writer will cannot get the lock when first writer get the lock
    // 3. The second writer will get the lock as last
    verify(writer1, timeout(10).atLeastOnce()).write();
    verify(writer2, after(10).never()).write();
    verify(writer2, timeout(100).atLeastOnce()).write();

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }
  }
}
