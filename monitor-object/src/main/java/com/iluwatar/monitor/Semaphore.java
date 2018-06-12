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
package com.iluwatar.monitor;import java.util.LinkedList;import java.util.ListIterator;/** * A FIFO semaphore. */public class Semaphore {  // Each queue element is a a single use semaphore  class QueueElement {    final int priority;    volatile boolean enabled = false;    QueueElement(int priority) {      this.priority = priority;    }    synchronized void acquire() {      while (!enabled) {        try {          wait();        } catch (InterruptedException e) {          throw new RuntimeException("Unexpected interruption of "            + "thread in monitor.Semaphore.acquire");        }      }    }    synchronized void release() {      enabled = true;      notify();    }  }  volatile int s1;  final LinkedList<QueueElement> queue = new LinkedList<QueueElement>();  // Invariant. All elements on the queue are in an unenabled state.  /** Initialize the semaphore to a value greater or equal to 0. */  public Semaphore(int initialvalue) {    Assertion.check(initialvalue >= 0);    this.s1 = initialvalue;  }  /**   * The P operation. If two threads are blocked at the same time, they will be   * served in FIFO order. <kbd>sem.acquire()</kbd> is equivalent to <kbd>acquire(   * Integer.MAX_VALUE )</kbd>.   */  public void acquire() {    acquire(Integer.MAX_VALUE);  }  /**   * The P operation with a priority.   *    * @param priority   *            The larger the integer, the less urgent the priority. If two   *            thread are waiting with equal priority, they will complete acquire   *            in FIFO order.   */  public void acquire(int priority) {    QueueElement mine;    synchronized (this) {      if (s1 > 0) {        --s1;        return;      }      mine = new QueueElement(priority);      if (priority == Integer.MAX_VALUE) {        queue.add(mine);      } else {        ListIterator<QueueElement> it = queue.listIterator(0);        int i = 0;        while (it.hasNext()) {          QueueElement elem = it.next();          if (elem.priority > priority) {            break;          }          ++i;        }        queue.add(i, mine);      }    }    mine.acquire();  }  /** The V operation. */  public synchronized void release() {    QueueElement first = queue.poll();    if (first != null) {      first.release();    } else {      ++s1;    }  }}