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

package com.iluwatar.leaderelection.ring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import com.iluwatar.leaderelection.AbstractInstance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import java.util.Queue;
import org.junit.jupiter.api.Test;

/**
 * RingInstance unit test.
 */
public class RingInstanceTest {

  @Test
  void testOnMessage() {
    try {
      final var ringInstance = new RingInstance(null, 1, 1);
      var ringMessage = new Message(MessageType.HEARTBEAT, "");
      ringInstance.onMessage(ringMessage);
      var ringInstanceClass = AbstractInstance.class;
      var messageQueueField = ringInstanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      assertEquals(ringMessage, ((Queue<Message>) messageQueueField.get(ringInstance)).poll());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("fail to access messasge queue.");
    }
  }

  @Test
  void testIsAlive() {
    try {
      final var ringInstance = new RingInstance(null, 1, 1);
      var ringInstanceClass = AbstractInstance.class;
      var aliveField = ringInstanceClass.getDeclaredField("alive");
      aliveField.setAccessible(true);
      aliveField.set(ringInstance, false);
      assertFalse(ringInstance.isAlive());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to access field alive.");
    }
  }

  @Test
  void testSetAlive() {
    final var ringInstance = new RingInstance(null, 1, 1);
    ringInstance.setAlive(false);
    assertFalse(ringInstance.isAlive());
  }
}
