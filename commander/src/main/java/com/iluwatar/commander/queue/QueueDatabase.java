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

import com.iluwatar.commander.Database;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.IsEmptyException;
import java.util.ArrayList;
import java.util.List;

/**
 * QueueDatabase id where the instructions to be implemented are queued.
 */

public class QueueDatabase extends Database<QueueTask> {

  private Queue<QueueTask> data;
  public List<Exception> exceptionsList;

  public QueueDatabase(Exception... exc) {
    this.data = new Queue<>();
    this.exceptionsList = new ArrayList<>(List.of(exc));
  }

  @Override
  public QueueTask add(QueueTask t) {
    data.enqueue(t);
    return t;
    //even if same thing queued twice, it is taken care of in other dbs
  }

  /**
   * peek method returns object at front without removing it from queue.
   *
   * @return object at front of queue
   * @throws IsEmptyException             if queue is empty
   */

  public QueueTask peek() throws IsEmptyException {
    return this.data.peek();
  }

  /**
   * dequeue method removes the object at front and returns it.
   *
   * @return object at front of queue
   * @throws IsEmptyException             if queue is empty
   */

  public QueueTask dequeue() throws IsEmptyException {
    return this.data.dequeue();
  }

  @Override
  public QueueTask get(String taskId) {
    return null;
  }

}
