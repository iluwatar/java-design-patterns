package com.iluwatar.reader.writer.lock;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hongshuwei@gmail.com
 */
public class ReaderAndWriterTest {

  ExecutorService executeService;

  @Before
  public void setup() {
    executeService = Executors.newFixedThreadPool(2);
  }

  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testReadAndWrite() throws Exception {

    ReaderWriterLock lock = new ReaderWriterLock();

    Reader reader1 = spy(new Reader("Reader 1", lock.readLock()));
    Writer writer1 = spy(new Writer("Writer 1", lock.writeLock()));

    executeService.submit(reader1);
    // Let reader1 execute first
    Thread.sleep(50);
    executeService.submit(writer1);

    verify(reader1, timeout(99).atLeastOnce()).read();
    verify(writer1, after(10).never()).write();
    verify(writer1, timeout(100).atLeastOnce()).write();

  }

  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testWriteAndRead() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Reader reader1 = spy(new Reader("Reader 1", lock.readLock()));
    Writer writer1 = spy(new Writer("Writer 1", lock.writeLock()));

    executeService.submit(writer1);
    // Let reader1 execute first
    Thread.sleep(50);
    executeService.submit(reader1);

    verify(writer1, timeout(99).atLeastOnce()).write();
    verify(reader1, after(10).never()).read();
    verify(reader1, timeout(100).atLeastOnce()).read();


  }

  @After
  public void tearDown() {
    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }
  }
}

