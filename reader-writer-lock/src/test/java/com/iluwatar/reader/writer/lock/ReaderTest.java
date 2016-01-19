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
public class ReaderTest extends StdOutTest {

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
    Thread.sleep(150);
    executeService.submit(reader2);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }

    // Read operation will hold the read lock 250 milliseconds, so here we prove that multiple reads
    // can be performed in the same time.
    final InOrder inOrder = inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("Reader 1 begin");
    inOrder.verify(getStdOutMock()).println("Reader 2 begin");
    inOrder.verify(getStdOutMock()).println("Reader 1 finish");
    inOrder.verify(getStdOutMock()).println("Reader 2 finish");

  }
}
