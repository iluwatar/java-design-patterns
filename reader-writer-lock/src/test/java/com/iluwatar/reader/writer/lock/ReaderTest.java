package com.iluwatar.reader.writer.lock;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author hongshuwei@gmail.com
 */
public class ReaderTest {

  /**
   * Verify that multiple readers can get the read lock concurrently
   */
  @Test
  public void testRead() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Reader reader1 = spy(new Reader("Reader 1", lock.readLock()));
    Reader reader2 = spy(new Reader("Reader 2", lock.readLock()));

    executeService.submit(reader1);
    executeService.submit(reader2);

    // Read operation will hold the read lock 100 milliseconds, so here we guarantee that each
    // readers can read in 99 milliseconds to prove that multiple read can perform in the same time.
    verify(reader1, timeout(99).atLeastOnce()).read();
    verify(reader2, timeout(99).atLeastOnce()).read();

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }
  }
}
