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

package com.iluwatar.leaderelection.bully;

import com.iluwatar.leaderelection.AbstractInstance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BullyInstance unit test.
 */
public class BullyinstanceTest {

  @Test
  public void testOnMessage() {
    try {
      final var bullyInstance = new BullyInstance(null, 1, 1);
      var bullyMessage = new Message(MessageType.HEARTBEAT, "");
      bullyInstance.onMessage(bullyMessage);
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      assertEquals(bullyMessage, ((Queue<Message>) messageQueueField.get(bullyInstance)).poll());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("fail to access messasge queue.");
    }

  }

  @Test
  public void testIsAlive() {
    try {
      final var bullyInstance = new BullyInstance(null, 1, 1);
      var instanceClass = AbstractInstance.class;
      var aliveField = instanceClass.getDeclaredField("alive");
      aliveField.setAccessible(true);
      aliveField.set(bullyInstance, false);
      assertFalse(bullyInstance.isAlive());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to access field alive.");
    }
  }

  @Test
  public void testSetAlive() {
    final var bullyInstance = new BullyInstance(null, 1, 1);
    bullyInstance.setAlive(false);
    assertFalse(bullyInstance.isAlive());
  }

}
