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
package com.iluwatar.leaderelection.ring;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * RingMessageManager unit test.
 */
class RingMessageManagerTest {

  @Test
  void testSendHeartbeatMessage() {
    var instance1 = new RingInstance(null, 1, 1);
    Map<Integer, Instance> instanceMap = Map.of(1, instance1);
    var messageManager = new RingMessageManager(instanceMap);
    assertTrue(messageManager.sendHeartbeatMessage(1));
  }

  @Test
  void testSendElectionMessage() {
    try {
      var instance1 = new RingInstance(null, 1, 1);
      var instance2 = new RingInstance(null, 1, 2);
      var instance3 = new RingInstance(null, 1, 3);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3);
      var messageManager = new RingMessageManager(instanceMap);
      var messageContent = "2";
      messageManager.sendElectionMessage(2, messageContent);
      var ringMessage = new Message(MessageType.ELECTION, messageContent);
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var ringMessageSent = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      assertEquals(ringMessageSent.getType(), ringMessage.getType());
      assertEquals(ringMessageSent.getContent(), ringMessage.getContent());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  void testSendLeaderMessage() {
    try {
      var instance1 = new RingInstance(null, 1, 1);
      var instance2 = new RingInstance(null, 1, 2);
      var instance3 = new RingInstance(null, 1, 3);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3);
      var messageManager = new RingMessageManager(instanceMap);
      var messageContent = "3";
      messageManager.sendLeaderMessage(2, 3);
      var ringMessage = new Message(MessageType.LEADER, messageContent);
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var ringMessageSent = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      assertEquals(ringMessageSent, ringMessage);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Error to access private field.");
    }
  }

  @Test
  void testSendHeartbeatInvokeMessage() {
    try {
      var instance1 = new RingInstance(null, 1, 1);
      var instance2 = new RingInstance(null, 1, 2);
      var instance3 = new RingInstance(null, 1, 3);
      Map<Integer, Instance> instanceMap = Map.of(1, instance1, 2, instance2, 3, instance3);
      var messageManager = new RingMessageManager(instanceMap);
      messageManager.sendHeartbeatInvokeMessage(2);
      var ringMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
      var instanceClass = AbstractInstance.class;
      var messageQueueField = instanceClass.getDeclaredField("messageQueue");
      messageQueueField.setAccessible(true);
      var ringMessageSent = ((Queue<Message>) messageQueueField.get(instance3)).poll();
      assertEquals(ringMessageSent.getType(), ringMessage.getType());
      assertEquals(ringMessageSent.getContent(), ringMessage.getContent());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Error to access private field.");
    }
  }

}
