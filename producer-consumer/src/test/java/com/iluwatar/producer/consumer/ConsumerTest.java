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

package com.iluwatar.producer.consumer;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/27/15 - 11:01 PM
 *
 * @author Jeroen Meulemeester
 */
public class ConsumerTest {

  private static final int ITEM_COUNT = 5;

  @Test
  public void testConsume() throws Exception {
    final ItemQueue queue = spy(new ItemQueue());
    for (int id = 0; id < ITEM_COUNT; id++) {
      queue.put(new Item("producer", id));
    }

    reset(queue); // Don't count the preparation above as interactions with the queue
    final Consumer consumer = new Consumer("consumer", queue);

    for (int id = 0; id < ITEM_COUNT; id++) {
      consumer.consume();
    }

    verify(queue, times(ITEM_COUNT)).take();
  }

}
