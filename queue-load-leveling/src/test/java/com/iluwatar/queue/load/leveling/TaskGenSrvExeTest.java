/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import org.junit.jupiter.api.Test;

/**
 * Test case for submitting Message to Blocking Queue by TaskGenerator and retrieve the message by
 * ServiceExecutor.
 */
public class TaskGenSrvExeTest {

  @Test
  public void taskGeneratorTest() {
    var msgQueue = new MessageQueue();

    // Create a task generator thread with 1 job to submit.
    var taskRunnable = new TaskGenerator(msgQueue, 1);
    var taskGenThr = new Thread(taskRunnable);
    taskGenThr.start();

    // Create a service executor thread.
    var srvRunnable = new ServiceExecutor(msgQueue);
    var srvExeThr = new Thread(srvRunnable);
    srvExeThr.start();
  }

}
