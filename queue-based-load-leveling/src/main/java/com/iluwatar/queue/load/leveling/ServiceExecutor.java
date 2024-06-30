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

package com.iluwatar.queue.load.leveling;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceExecutor class. This class will pick up Messages one by one from the
 * Blocking Queue and
 * process them.
 */
@Slf4j
public class ServiceExecutor implements Runnable {

  private final MessageQueue msgQueue;
  private volatile boolean isRunning = true;

  public ServiceExecutor(MessageQueue msgQueue) {
    this.msgQueue = msgQueue;
  }

  /**
   * The ServiceExecutor thread will retrieve each message and process it.
   */
  @Override
  public void run() {
    try {
      while (isRunning) {
        var msg = msgQueue.retrieveMsg();

        if (msg != null) {
          LOGGER.info(msg + " is served.");
        } else {
          LOGGER.info("Service Executor: Waiting for Messages to serve .. ");
        }

        // Simulating processing time
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Reset interrupt status
      LOGGER.error("ServiceExecutor thread interrupted", e);
    }
  }

  /**
   * Stops the execution of the ServiceExecutor thread.
   */
  public void stop() {
    isRunning = false;
  }
}
