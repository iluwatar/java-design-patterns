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
package com.iluwatar.twin;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents a UI thread for drawing the {@link BallItem} and provides methods
 * for suspension and resumption. It holds a reference to {@link BallItem} to delegate the draw task.
 */
@Slf4j
public class BallThread extends Thread {

  @Setter
  private BallItem twin;

  private final Lock lock = new ReentrantLock();
  private final Condition notSuspended = lock.newCondition();

  private volatile boolean isSuspended = false;
  private volatile boolean isRunning = true;

  /**
   * Run the thread to continuously draw and move the ball unless suspended.
   */
  @Override
  public void run() {
    while (isRunning) {
      lock.lock();
      try {
        while (isSuspended) {
          LOGGER.info("BallThread suspended.");
          notSuspended.await(); // Wait until resumed
        }
      } catch (InterruptedException e) {
        LOGGER.info("BallThread interrupted.", e);
        Thread.currentThread().interrupt(); // Re-interrupt the thread for proper handling
        break;
      } finally {
        lock.unlock();
      }

      // Perform the twin's tasks if not suspended
      try {
        twin.draw();
        twin.move();
        Thread.sleep(250); // Introduce a controlled interval between actions
      } catch (InterruptedException e) {
        LOGGER.info("BallThread interrupted during sleep.", e);
        Thread.currentThread().interrupt(); // Handle interrupt properly
        break;
      }
    }

    LOGGER.info("BallThread has stopped.");
  }

  /**
   * Suspend the thread's operations.
   */
  public void suspendMe() {
    lock.lock();
    try {
      isSuspended = true;
      LOGGER.info("Suspending BallThread.");
    } finally {
      lock.unlock();
    }
  }

  /**
   * Resume the thread's operations.
   */
  public void resumeMe() {
    lock.lock();
    try {
      isSuspended = false;
      notSuspended.signal(); // Notify the thread to resume
      LOGGER.info("Resuming BallThread.");
    } finally {
      lock.unlock();
    }
  }

  /**
   * Stop the thread's operations.
   */
  public void stopMe() {
    isRunning = false;
    this.interrupt(); // Interrupt the thread to terminate
    LOGGER.info("Stopping BallThread.");
  }
}
