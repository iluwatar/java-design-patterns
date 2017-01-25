/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.iluwatar.leaderfollower;

import java.util.List;

public class Thread implements Runnable {

  private final HandleSet handleSet;
  private List<Thread> threads;
  private final int id;
  private final ThreadPool threadPool;
  private final ConcreteEventHandler concreteEventHandler;

  /**
   * Thread constructor
   */
  Thread(HandleSet handleSet, List<Thread> threads, int id, ThreadPool threadPool,
         ConcreteEventHandler concreteEventHandler) {
    this.handleSet = handleSet;
    this.threads = threads;
    this.id = id;
    this.threadPool = threadPool;
    this.concreteEventHandler = concreteEventHandler;
  }


  /**
   * Promotes a new Leader and notifies all the threads
   */
  private void becomeLeader(Thread thread) {
    synchronized (threadPool) {
      threadPool.notifyAll();
    }
    threadPool.promoteNewLeader(thread);
  }

  int getId() {
    return id;
  }

  /**
   * Method is called when thread is executed.
   * Makes Leader thread to listen to input and handle evens. Followers are waiting to get promoted.
   **/
  public void run() {
    while (!handleSet.isEmpty()) {
      try {
        if (threadPool.getLeader() != this && threadPool.getLeader() != null) {
          synchronized (threadPool) {
            threadPool.wait();
          }
        }
        threads.remove(this);
        System.out.println("Thread: " + id + " became a leader");

        if (!threads.isEmpty()) {
          becomeLeader(threadPool.getThreads().get(0));

        } else {
          becomeLeader(null);
        }

        Event event = handleSet.getEvent();
        concreteEventHandler.handleEvent(event, this);
        java.lang.Thread.sleep(event.getDistance());
        System.out.println("Thread: " + id + " completed the task. Travelled " + event.getDistance() + " miles");
        threadPool.addThread(this);

      } catch (InterruptedException e) {
        System.out.println("interrupted");
        return;
      }
    }
  }
}
