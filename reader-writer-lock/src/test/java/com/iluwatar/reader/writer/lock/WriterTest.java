package com.iluwatar.reader.writer.lock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mockito.InOrder;

/**
 * @author hongshuwei@gmail.com
 */
public class WriterTest extends StdOutTest {

  /**
   * Verify that multiple writers will get the lock in order.
   */
  @Test
  public void testWrite() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Writer writer1 = spy(new Writer("Writer 1", lock.writeLock()));
    Writer writer2 = spy(new Writer("Writer 2", lock.writeLock()));

    executeService.submit(writer1);
    // Let write1 execute first
    Thread.sleep(150);
    executeService.submit(writer2);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }
    // Write operation will hold the write lock 250 milliseconds, so here we verify that when two
    // writer execute concurrently, the second writer can only writes only when the first one is
    // finished.
    final InOrder inOrder = inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("Writer 1 begin");
    inOrder.verify(getStdOutMock()).println("Writer 1 finish");
    inOrder.verify(getStdOutMock()).println("Writer 2 begin");
    inOrder.verify(getStdOutMock()).println("Writer 2 finish");
  }
}
