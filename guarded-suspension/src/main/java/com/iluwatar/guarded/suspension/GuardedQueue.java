/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.guarded.suspension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Guarded Queue is an implementation for Guarded Suspension Pattern
 * Guarded suspension pattern is used to handle a situation when you want to execute a method
 * on an object which is not in a proper state.
 * @see http://java-design-patterns.com/patterns/guarded-suspension/
 */
public class GuardedQueue {
  private static final Logger LOGGER = LoggerFactory.getLogger(GuardedQueue.class);
  private final Queue<Integer> sourceList;

  public GuardedQueue() {
    this.sourceList = new LinkedList<>();
  }

  /**
   * @return last element of a queue if queue is not empty
   */
  public synchronized Integer get() {
    while (sourceList.isEmpty()) {
      try {
        LOGGER.info("waiting");
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    LOGGER.info("getting");
    return sourceList.peek();
  }

  /**
   * @param e number which we want to put to our queue
   */
  public synchronized void put(Integer e) {
    LOGGER.info("putting");
    sourceList.add(e);
    LOGGER.info("notifying");
    notify();
  }
}
