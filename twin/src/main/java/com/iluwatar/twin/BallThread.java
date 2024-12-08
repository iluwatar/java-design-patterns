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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provides methods for suspend
 * and resume. It holds the reference of {@link BallItem} to delegate the draw task.
 */
@Slf4j
public class BallThread extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(BallThread.class);

  private volatile boolean isRunning = true;
  private volatile boolean isSuspended = false;
  private final Object lock = new Object();
  private final BallItem twin;

  /**
   * Constructor.
   *
   * @param twin the BallItem instance
   */
  public BallThread(BallItem twin) {
    this.twin = twin;
  }

  @Override
  public void run() {
    try {
      while (isRunning) {
        synchronized (lock) {
          while (isSuspended) {
            lock.wait();
          }
        }
        twin.doDraw();
        twin.move();
        Thread.sleep(250);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  /**
   * Suspend the thread.
   */
  public void suspendMe() {
    isSuspended = true;
    LOGGER.info("Begin to suspend BallThread");
  }

  /**
   * Notify run to resume.
   */
  public void resumeMe() {
    synchronized (lock) {
      isSuspended = false;
      lock.notifyAll();
    }
    LOGGER.info("Begin to resume BallThread");
  }

  /**
   * Stop running thread.
   */
  public void stopMe() {
    isRunning = false;
    resumeMe(); // Ensure the thread exits if it is waiting
  }
}
