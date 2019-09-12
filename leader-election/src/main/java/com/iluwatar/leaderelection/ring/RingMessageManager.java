/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import com.iluwatar.leaderelection.MessageType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of Ring message manager
 */
public class RingMessageManager implements MessageManager {

  private Map<Integer, Instance> instanceMap;

  /**
   * Constructor of RingMessageManager.
   */
  public RingMessageManager(Map<Integer, Instance> instanceMap) {
    this.instanceMap = instanceMap;
  }

  /**
   * Send heartbeat message to current leader instance to check the health.
   * @param leaderId leaderID
   * @return {@code true} if the leader is alive.
   */
  @Override
  public boolean sendHeartbeatMessage(int leaderId) {
    Instance leaderInstance = instanceMap.get(leaderId);
    boolean alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Send election message to the next instance.
   * @param currentId currentID
   * @param content list contains all the IDs of instances which have received this election message.
   */
  @Override
  public void sendElectionMessage(int currentId, String content) {
    Instance nextInstance = this.findNextInstance(currentId);
    Message electionMessage = new RingMessage(MessageType.ELECTION, content);
    nextInstance.onMessage(electionMessage);
  }

  /**
   * Send leader message to the next instance.
   */
  @Override
  public void sendLeaderMessage(int currentId, int leaderId) {
    Instance nextInstance = this.findNextInstance(currentId);
    Message leaderMessage = new RingMessage(MessageType.LEADER, String.valueOf(leaderId));
    nextInstance.onMessage(leaderMessage);
  }

  /**
   * Send heartbeat invoke message to the next instance.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    Instance nextInstance = this.findNextInstance(currentId);
    Message heartbeatInvokeMessage = new RingMessage(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

  /**
   * Find the next instance in the ring.
   * @return The next instance.
   */
  private Instance findNextInstance(int currentId) {
    Instance result = null;
    List<Integer> candidateSet =
        instanceMap.keySet()
                      .stream()
                      .filter((i) -> i > currentId && instanceMap.get(i).isAlive())
                      .sorted()
                      .collect(Collectors.toList());
    if (candidateSet.isEmpty()) {
      int index =
          instanceMap.keySet()
                        .stream()
                        .filter((i) -> instanceMap.get(i).isAlive())
                        .sorted()
                        .collect(Collectors.toList())
                        .get(0);
      result = instanceMap.get(index);
    } else {
      int index = candidateSet.get(0);
      result = instanceMap.get(index);
    }
    return result;
  }
}
