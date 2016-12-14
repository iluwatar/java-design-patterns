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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is the asynchronous layer which does not block when a new request arrives. It just passes
 * the request to the synchronous layer which consists of a queue i.e. a {@link BlockingQueue} and a
 * pool of threads i.e. {@link ThreadPoolExecutor}. Out of this pool of worker threads one of the
 * thread picks up the task and executes it synchronously in background and the result is posted
 * back to the caller via callback.
 */
public class AsynchronousService {

  /*
   * This represents the queuing layer as well as synchronous layer of the pattern. The thread pool
   * contains worker threads which execute the tasks in blocking/synchronous manner. Long running
   * tasks should be performed in the background which does not affect the performance of main
   * thread.
   */
  private ExecutorService service;

  /**
   * Creates an asynchronous service using {@code workQueue} as communication channel between
   * asynchronous layer and synchronous layer. Different types of queues such as Priority queue, can
   * be used to control the pattern of communication between the layers.
   */
  public AsynchronousService(BlockingQueue<Runnable> workQueue) {
    service = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, workQueue);
  }


  /**
   * A non-blocking method which performs the task provided in background and returns immediately.
   * <p>
   * On successful completion of task the result is posted back using callback method
   * {@link AsyncTask#onPostCall(Object)}, if task execution is unable to complete normally due to
   * some exception then the reason for error is posted back using callback method
   * {@link AsyncTask#onError(Throwable)}.
   * <p>
   * NOTE: The results are posted back in the context of background thread in this implementation.
   */
  public <T> void execute(final AsyncTask<T> task) {
    try {
      // some small tasks such as validation can be performed here.
      task.onPreCall();
    } catch (Exception e) {
      task.onError(e);
      return;
    }

    service.submit(new FutureTask<T>(task) {
      @Override
      protected void done() {
        super.done();
        try {
          /*
           * called in context of background thread. There is other variant possible where result is
           * posted back and sits in the queue of caller thread which then picks it up for
           * processing. An example of such a system is Android OS, where the UI elements can only
           * be updated using UI thread. So result must be posted back in UI thread.
           */
          task.onPostCall(get());
        } catch (InterruptedException e) {
          // should not occur
        } catch (ExecutionException e) {
          task.onError(e.getCause());
        }
      }
    });
  }
}
