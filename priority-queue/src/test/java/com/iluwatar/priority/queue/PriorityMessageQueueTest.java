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

package com.iluwatar.priority.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test case for order of messages
 */
public class PriorityMessageQueueTest {


  @Test
  public void remove() {
    var stringPriorityMessageQueue = new PriorityMessageQueue<>(new String[2]);
    var pushMessage = "test";
    stringPriorityMessageQueue.add(pushMessage);
    assertEquals(stringPriorityMessageQueue.remove(), pushMessage);
  }

  @Test
  public void add() {
    var stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.add(5);
    stringPriorityMessageQueue.add(10);
    stringPriorityMessageQueue.add(3);
    assertEquals(10, (int) stringPriorityMessageQueue.remove());
  }

  @Test
  public void isEmpty() {
    var stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    assertTrue(stringPriorityMessageQueue.isEmpty());
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.remove();
    assertTrue(stringPriorityMessageQueue.isEmpty());
  }

  @Test
  public void testEnsureSize() {
    var stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    assertTrue(stringPriorityMessageQueue.isEmpty());
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.add(2);
    stringPriorityMessageQueue.add(2);
    stringPriorityMessageQueue.add(3);
    assertEquals(3, (int) stringPriorityMessageQueue.remove());
  }
}