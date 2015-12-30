package com.iluwatar.threadpool;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/30/15 - 18:21 PM
 *
 * @author Jeroen Meulemeester
 */
public class WorkerTest {

  /**
   * Verify if a worker does the actual job
   */
  @Test
  public void testRun() {
    final Task task = mock(Task.class);
    final Worker worker = new Worker(task);
    verifyZeroInteractions(task);

    worker.run();
    verify(task).getTimeMs();
    verifyNoMoreInteractions(task);
  }

}