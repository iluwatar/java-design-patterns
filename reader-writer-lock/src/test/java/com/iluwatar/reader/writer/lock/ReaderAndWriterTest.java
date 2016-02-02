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

import static org.mockito.Mockito.inOrder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mockito.InOrder;

/**
 * @author hongshuwei@gmail.com
 */
public class ReaderAndWriterTest extends StdOutTest {



  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testReadAndWrite() throws Exception {

    ReaderWriterLock lock = new ReaderWriterLock();

    Reader reader1 = new Reader("Reader 1", lock.readLock());
    Writer writer1 = new Writer("Writer 1", lock.writeLock());

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    executeService.submit(reader1);
    // Let reader1 execute first
    Thread.sleep(150);
    executeService.submit(writer1);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }

    final InOrder inOrder = inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("Reader 1 begin");
    inOrder.verify(getStdOutMock()).println("Reader 1 finish");
    inOrder.verify(getStdOutMock()).println("Writer 1 begin");
    inOrder.verify(getStdOutMock()).println("Writer 1 finish");
  }

  /**
   * Verify reader and writer can only get the lock to read and write orderly
   */
  @Test
  public void testWriteAndRead() throws Exception {

    ExecutorService executeService = Executors.newFixedThreadPool(2);
    ReaderWriterLock lock = new ReaderWriterLock();

    Reader reader1 = new Reader("Reader 1", lock.readLock());
    Writer writer1 = new Writer("Writer 1", lock.writeLock());

    executeService.submit(writer1);
    // Let writer1 execute first
    Thread.sleep(150);
    executeService.submit(reader1);

    executeService.shutdown();
    try {
      executeService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("Error waiting for ExecutorService shutdown");
    }

    final InOrder inOrder = inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("Writer 1 begin");
    inOrder.verify(getStdOutMock()).println("Writer 1 finish");
    inOrder.verify(getStdOutMock()).println("Reader 1 begin");
    inOrder.verify(getStdOutMock()).println("Reader 1 finish");
  }
}

