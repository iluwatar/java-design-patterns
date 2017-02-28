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
/**
 * Guarded-suspension is a concurrent design pattern for handling situation when to execute some action we need
 * condition to be satisfied.
 * <p>
 * Implementation is based on GuardedQueue, which has two methods: get and put,
 * the condition is that we cannot get from empty queue so when thread attempt
 * to break the condition we invoke Object's wait method on him and when other thread put an element
 * to the queue he notify the waiting one that now he can get from queue.
 */
package com.iluwatar.guarded.suspension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by robertt240 on 1/26/17.
 */
public class App {
  /**
   * Example pattern execution
   *
   * @param args - command line args
   */
  public static void main(String[] args) {
    GuardedQueue guardedQueue = new GuardedQueue();
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    //here we create first thread which is supposed to get from guardedQueue
    executorService.execute(() -> {
          guardedQueue.get();
        }
    );

    //here we wait two seconds to show that the thread which is trying to get from guardedQueue will be waiting
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //now we execute second thread which will put number to guardedQueue and notify first thread that it could get
    executorService.execute(() -> {
          guardedQueue.put(20);
        }
    );
    executorService.shutdown();
    try {
      executorService.awaitTermination(30, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
