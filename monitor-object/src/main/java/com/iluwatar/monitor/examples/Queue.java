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