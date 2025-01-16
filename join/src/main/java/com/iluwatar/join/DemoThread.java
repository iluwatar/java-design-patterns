/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.join;

import lombok.extern.slf4j.Slf4j;

/**
 * demo threads implementing Runnable .
 */
@Slf4j
public class DemoThread implements Runnable {

  private static int[] actualExecutionOrder;
  private static int index = 0;
  private static JoinPattern pattern;
  private final int id;
  private final Thread previous;
  
  /**
   * Initialise a demo thread object with id and previous thread .
   */
  public DemoThread(int id, Thread previous) {
    this.id = id;
    this.previous = previous;

  }

  public static int[] getActualExecutionOrder() {
    return actualExecutionOrder;
  }
  /**
   * set custom execution order of threads .
   */
  public static void setExecutionOrder(int[] executionOrder, JoinPattern pattern) {
    DemoThread.pattern = pattern;
    actualExecutionOrder = new int[executionOrder.length];
  }
  /**
   * use to run demo thread.
   * every newly created thread waits for 
   * the completion of previous thread 
   * by previous.join() .
   */
  @Override
  public void run() {
    if (previous != null) {
      try {
        previous.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Interrupted exception : ", e);
      }
    }
    LOGGER.info("Thread " + id + " starts");
    try {
      Thread.sleep(id * (long) 250);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted exception : ", e);
    } finally {
      LOGGER.info("Thread " + id + " ends");
      actualExecutionOrder[index++] = id;
      pattern.countdown();
    }
  }

}
