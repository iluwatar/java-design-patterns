/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
 * ServiceExecuotr class. This class will pick up Messages one by one from the Blocking Queue and
 * process them.
 */
@Slf4j
public class ServiceExecutor implements Runnable {

  private final MessageQueue msgQueue;

  public ServiceExecutor(MessageQueue msgQueue) {
    this.msgQueue = msgQueue;
  }

  /**
   * The ServiceExecutor thread will retrieve each message and process it.
   */
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        var msg = msgQueue.retrieveMsg();

        if (null != msg) {
          LOGGER.info(msg.toString() + " is served.");
        } else {
          LOGGER.info("Service Executor: Waiting for Messages to serve .. ");
        }

        Thread.sleep(1000);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}