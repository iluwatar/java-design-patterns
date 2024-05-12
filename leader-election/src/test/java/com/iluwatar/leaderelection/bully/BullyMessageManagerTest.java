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
package com.iluwatar.leaderelection.bully;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.iluwatar.leaderelection.AbstractInstance;
import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import java.util.Map;
import java.util.Queue;
import org.junit.jupiter.api.Test;

/**
 * BullyMessageManager unit test.
 */
class BullyMessageManagerTest {

  @Test
  void testSendHeartbeatMessage() {
    var instance1 = new BullyInstance(null, 1, 1);
    Map<Integer, Instance> instanceMap = Map.of(1, instance1);
    var messageManager = new BullyMessageManager(instanceMap);
    assertTrue(messageManager.sendHeartbeatMessage(1));
  }

  @Test
  void testSendElectionMessageNotAccepted() {
    try {
      var instance1 = new BullyInstance(null, 1, 1);
      var instance2 = new BullyInstance(null, 1, 2);
      var instance3 = new BullyInstance(null, 1, 3);
      var instance4 = new BullyInstance(null, 1, 4);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
      instance1.setAlive(false);
      var messageManager = new BullyMessageManager(instanceMap);
      var result = messageManager.sendElectionMessage(3, "3");
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var message2 = ((Queue<Message>) messageQueueField.get(instance2)).poll();
      var instance4QueueSize = ((Queue<Message>) messageQueueField.get(instance4)).size();
      var expectedMessage = new Message(MessageType.ELECTION_INVOKE, "");
      assertEquals(message2, expectedMessage);
      assertEquals(instance4QueueSize, 0);
      assertFalse(result);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  void testElectionMessageAccepted() {
    var instance1 = new BullyInstance(null, 1, 1);
    var instance2 = new BullyInstance(null, 1, 2);
    var instance3 = new BullyInstance(null, 1, 3);
    var instance4 = new BullyInstance(null, 1, 4);
    Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
    instance1.setAlive(false);
    var messageManager = new BullyMessageManager(instanceMap);
    var result = messageManager.sendElectionMessage(2, "2");
    assertTrue(result);
  }

  @Test
  void testSendLeaderMessage() {
    try {
      var instance1 = new BullyInstance(null, 1, 1);
      var instance2 = new BullyInstance(null, 1, 2);
      var instance3 = new BullyInstance(null, 1, 3);
      var instance4 = new BullyInstance(null, 1, 4);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3, 4, instance4);
      instance1.setAlive(false);
      var messageManager = new BullyMessageManager(instanceMap);
      messageManager.sendLeaderMessage(2, 2);
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var message3 = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      var message4 = ((Queue<Message>) messageQueueField.get(instance4)).poll();
      var expectedMessage = new Message(MessageType.LEADER, "2");
      assertEquals(message3, expectedMessage);
      assertEquals(message4, expectedMessage);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  void testSendHeartbeatInvokeMessage() {
    try {
      var instance1 = new BullyInstance(null, 1, 1);
      var instance2 = new BullyInstance(null, 1, 2);
      var instance3 = new BullyInstance(null, 1, 3);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3);
      var messageManager = new BullyMessageManager(instanceMap);
      messageManager.sendHeartbeatInvokeMessage(2);
      var message = new Message(MessageType.HEARTBEAT_INVOKE, "");
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var messageSent = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      assertEquals(messageSent.getType(), message.getType());
      assertEquals(messageSent.getContent(), message.getContent());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Error to access private field.");
    }
  }


}
