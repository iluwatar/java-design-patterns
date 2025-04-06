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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * MessageQueue class. In this class we will create a Blocking Queue and submit/retrieve all the
 * messages from it.
 */
@Slf4j
public class MessageQueue {

  private final BlockingQueue<Message> blkQueue;

  // Default constructor when called creates Blocking Queue object.
  public MessageQueue() {
    this.blkQueue = new ArrayBlockingQueue<>(1024);
  }

  /**
   * All the TaskGenerator threads will call this method to insert the Messages in to the Blocking
   * Queue.
   */
  public void submitMsg(Message msg) {
    try {
      if (null != msg) {
        blkQueue.add(msg);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * All the messages will be retrieved by the ServiceExecutor by calling this method and process
   * them. Retrieves and removes the head of this queue, or returns null if this queue is empty.
   */
  public Message retrieveMsg() {
    try {
      return blkQueue.poll();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return null;
  }
}
