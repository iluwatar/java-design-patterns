package com.iluwatar.halfsynchalfasync;

import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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