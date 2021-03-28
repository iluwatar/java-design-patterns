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

import com.iluwatar.leaderelection.AbstractMessageManager;
import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import java.util.Map;

/**
 * Implementation of RingMessageManager.
 */
public class RingMessageManager extends AbstractMessageManager {

  /**
   * Constructor of RingMessageManager.
   */
  public RingMessageManager(Map<Integer, Instance> instanceMap) {
    super(instanceMap);
  }

  /**
   * Send heartbeat message to current leader instance to check the health.
   *
   * @param leaderId leaderID
   * @return {@code true} if the leader is alive.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    var leaderInstance = instanceMap.get(leaderId);
    var alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Send election message to the next instance.
   *
   * @param currentId currentID
   * @param content   list contains all the IDs of instances which have received this election
   *                  message.
   * @return {@code true} if the election message is accepted by the target instance.
   */
  @Override
  public boolean sendElectionMessage(int currentId, String content) {
    var nextInstance = this.findNextInstance(currentId);
    var electionMessage = new Message(MessageType.ELECTION, content);
    nextInstance.onMessage(electionMessage);
    return true;
  }

  /**
   * Send leader message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the leader message is accepted by the target instance.
   */
  @Override
  public boolean sendLeaderMessage(int currentId, int leaderId) {
    var nextInstance = this.findNextInstance(currentId);
    var leaderMessage = new Message(MessageType.LEADER, String.valueOf(leaderId));
    nextInstance.onMessage(leaderMessage);
    return true;
  }

  /**
   * Send heartbeat invoke message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    var nextInstance = this.findNextInstance(currentId);
    var heartbeatInvokeMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

}
