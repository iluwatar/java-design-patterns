/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.halfsynchalfasync;

import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Date: 12/12/15 - 11:15 PM
 *
 * @author Jeroen Meulemeester
 */
public class AsynchronousServiceTest {

  @Test
  public void testPerfectExecution() throws Exception {
    final AsynchronousService service = new AsynchronousService(new LinkedBlockingQueue<>());
    final AsyncTask<Object> task = mock(AsyncTask.class);
    final Object result = new Object();
    when(task.call()).thenReturn(result);
    service.execute(task);

    verify(task, timeout(2000)).onPostCall(eq(result));

    final InOrder inOrder = inOrder(task);
    inOrder.verify(task, times(1)).onPreCall();
    inOrder.verify(task, times(1)).call();
    inOrder.verify(task, times(1)).onPostCall(eq(result));

    verifyNoMoreInteractions(task);
  }

  @Test
  public void testCallException() throws Exception {
    final AsynchronousService service = new AsynchronousService(new LinkedBlockingQueue<>());
    final AsyncTask<Object> task = mock(AsyncTask.class);
    final IOException exception = new IOException();
    when(task.call()).thenThrow(exception);
    service.execute(task);

    verify(task, timeout(2000)).onError(eq(exception));

    final InOrder inOrder = inOrder(task);
    inOrder.verify(task, times(1)).onPreCall();
    inOrder.verify(task, times(1)).call();
    inOrder.verify(task, times(1)).onError(exception);

    verifyNoMoreInteractions(task);
  }

  @Test
  public void testPreCallException() throws Exception {
    final AsynchronousService service = new AsynchronousService(new LinkedBlockingQueue<>());
    final AsyncTask<Object> task = mock(AsyncTask.class);
    final IllegalStateException exception = new IllegalStateException();
    doThrow(exception).when(task).onPreCall();
    service.execute(task);

    verify(task, timeout(2000)).onError(eq(exception));

    final InOrder inOrder = inOrder(task);
    inOrder.verify(task, times(1)).onPreCall();
    inOrder.verify(task, times(1)).onError(exception);

    verifyNoMoreInteractions(task);
  }

}