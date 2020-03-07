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

import com.iluwatar.leaderelection.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BullyMessageManager unit test.
 */
public class BullyMessageManagerTest {

  @Test
  public void testSendHeartbeatMessage() {
    Instance instance1 = new BullyInstance(null, 1, 1);
    Map<Integer, Instance> instanceMap = Map.of(1, instance1);
    MessageManager messageManager = new BullyMessageManager(instanceMap);
    assertTrue(messageManager.sendHeartbeatMessage(1));
  }

  @Test
  public void testSendElectionMessageNotAccepted() {
    try {
      Instance instance1 = new BullyInstance(null, 1, 1);
      Instance instance2 = new BullyInstance(null, 1, 2);
      Instance instance3 = new BullyInstance(null, 1, 3);
      Instance instance4 = new BullyInstance(null, 1, 4);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
      instance1.setAlive(false);
      MessageManager messageManager = new BullyMessageManager(instanceMap);
      boolean result = messageManager.sendElectionMessage(3, "3");
      Class instanceClass = AbstractInstance.class;
      Field messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      Message message2 = ((Queue<Message>) messageQueueField.get(instance2)).poll();
      int instance4QueueSize = ((Queue<Message>) messageQueueField.get(instance4)).size();
      Message expectedMessage = new Message(MessageType.ELECTION_INVOKE, "");
      assertEquals(message2, expectedMessage);
      assertEquals(instance4QueueSize, 0);
      assertEquals(result, false);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  public void testElectionMessageAccepted() {
    Instance instance1 = new BullyInstance(null, 1, 1);
    Instance instance2 = new BullyInstance(null, 1, 2);
    Instance instance3 = new BullyInstance(null, 1, 3);
    Instance instance4 = new BullyInstance(null, 1, 4);
    Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
    instance1.setAlive(false);
    MessageManager messageManager = new BullyMessageManager(instanceMap);
    boolean result = messageManager.sendElectionMessage(2, "2");
    assertEquals(result, true);
  }

  @Test
  public void testSendLeaderMessage() {
    try {
      Instance instance1 = new BullyInstance(null, 1, 1);
      Instance instance2 = new BullyInstance(null, 1, 2);
      Instance instance3 = new BullyInstance(null, 1, 3);
      Instance instance4 = new BullyInstance(null, 1, 4);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
      instance1.setAlive(false);
      MessageManager messageManager = new BullyMessageManager(instanceMap);
      messageManager.sendLeaderMessage(2, 2);
      Class instanceClass = AbstractInstance.class;
      Field messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      Message message3 = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      Message message4 = ((Queue<Message>) messageQueueField.get(instance4)).poll();
      Message expectedMessage = new Message(MessageType.LEADER, "2");
      assertEquals(message3, expectedMessage);
      assertEquals(message4, expectedMessage);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  public void testSendHeartbeatInvokeMessage() {
    try {
      Instance instance1 = new BullyInstance(null, 1, 1);
      Instance instance2 = new BullyInstance(null, 1, 2);
      Instance instance3 = new BullyInstance(null, 1, 3);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3);
      MessageManager messageManager = new BullyMessageManager(instanceMap);
      messageManager.sendHeartbeatInvokeMessage(2);
      Message message = new Message(MessageType.HEARTBEAT_INVOKE, "");
      Class instanceClass = AbstractInstance.class;
      Field messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      Message messageSent = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      assertEquals(messageSent.getType(), message.getType());
      assertEquals(messageSent.getContent(), message.getContent());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Error to access private field.");
    }
  }


}
