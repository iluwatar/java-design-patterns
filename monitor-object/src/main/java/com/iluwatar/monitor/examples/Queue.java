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
package com.iluwatar.monitor.examples;

import com.iluwatar.monitor.AbstractMonitor;
import com.iluwatar.monitor.Condition;

/**
 * FIFO Queue implementation using {@link AbstractMonitor}.
 */
public class Queue extends AbstractMonitor {

  private final int capacity = 10;

  private final Object[] queue = new Object[capacity];

  private volatile int count = 0;
  
  private volatile int front = 0;

  /** Awaiting ensures: count < capacity. */
  private final Condition notFull = makeCondition();

  /** Awaiting ensures: count > 0. */
  private final Condition notEmpty = makeCondition();

  /**
   * Method to pop the front element from queue.
   * @return the top most element
   */
  public Object fetch() {
    enter();
    if (!(count > 0)) {
      notEmpty.await();
      assert count > 0;
    }
    count--;
    front = (front + 1) % capacity;
    assert count < capacity;
    notFull.signal();
    leave();
    Object value = queue[front];
    return value;
  }

  /**
   * Method to push an element in queue.
   * @param value the element to be pushed
   */
  public void deposit(Object value) {
    enter();
    if (!(count < capacity)) {
      notFull.await();
      assert count < capacity;
    }
    queue[(front + count) % capacity] = value;
    count++;
    assert count > 0;
    notEmpty.signal();
    leave();
  }

  @Override
  protected boolean invariant() {
    return 0 <= count && count <= capacity && 0 <= front && front < capacity;
  }

}