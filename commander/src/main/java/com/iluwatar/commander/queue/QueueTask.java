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

package com.iluwatar.commander.queue;

import com.iluwatar.commander.Order;

/**
 * QueueTask object is the object enqueued in queue.
 */

public class QueueTask {

  /**
   * TaskType is the type of task to be done.
   */

  public enum TaskType {
    Messaging, Payment, EmployeeDb
  }

  public Order order;
  public TaskType taskType;
  public int messageType; //0-fail, 1-error, 2-success
  /*we could have varargs Object instead to pass in any parameter instead of just message type
  but keeping it simple here*/
  public long firstAttemptTime; //when first time attempt made to do task

  /**
   * QueueTask constructor.
   *
   * @param o           is the order for which the queuetask is being created
   * @param t           is the type of task to be done
   * @param messageType if it is a message, which type of message - this could have instead been
   *                    object varargs, and contained all additional details related to tasktype.
   */

  public QueueTask(Order o, TaskType t, int messageType) {
    this.order = o;
    this.taskType = t;
    this.messageType = messageType;
    this.firstAttemptTime = -1;
  }

  /**
   * getType method.
   *
   * @return String representing type of task
   */
  public String getType() {
    if (!this.taskType.equals(TaskType.Messaging)) {
      return this.taskType.toString();
    } else {
      if (this.messageType == 0) {
        return "Payment Failure Message";
      } else if (this.messageType == 1) {
        return "Payment Error Message";
      } else {
        return "Payment Success Message";
      }
    }
  }
}
