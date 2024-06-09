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
 * TaskGenerator class. Each TaskGenerator thread will be a Worker which submits messages to the
 * queue. We need to mention the message count for each of the TaskGenerator threads.
 */
@Slf4j
public class TaskGenerator implements Task, Runnable {

  // MessageQueue reference using which we will submit our messages.
  private final MessageQueue msgQueue;

  // Total message count that a TaskGenerator will submit.
  private final int msgCount;

  // Parameterized constructor.
  public TaskGenerator(MessageQueue msgQueue, int msgCount) {
    this.msgQueue = msgQueue;
    this.msgCount = msgCount;
  }

  /**
   * Submit messages to the Blocking Queue.
   */
  public void submit(Message msg) {
    try {
      this.msgQueue.submitMsg(msg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * Each TaskGenerator thread will submit all the messages to the Queue. After every message
   * submission TaskGenerator thread will sleep for 1 second.
   */
  public void run() {
    var count = this.msgCount;

    try {
      while (count > 0) {
        var statusMsg = "Message-" + count + " submitted by " + Thread.currentThread().getName();
        this.submit(new Message(statusMsg));

        LOGGER.info(statusMsg);

        // reduce the message count.
        count--;

        // Make the current thread to sleep after every Message submission.
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}