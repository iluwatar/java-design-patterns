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

import com.iluwatar.leaderelection.AbstractMessageManager;
import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of BullyMessageManager.
 */
public class BullyMessageManager extends AbstractMessageManager {

  /**
   * Constructor of BullyMessageManager.
   */
  public BullyMessageManager(Map<Integer, Instance> instanceMap) {
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
    Instance leaderInstance = instanceMap.get(leaderId);
    boolean alive = leaderInstance.isAlive();
    return alive;
  }

  /**
   * Send election message to all the instances with smaller ID.
   *
   * @param currentId Instance ID of which sends this message.
   * @param content   Election message content.
   * @return {@code true} if no alive instance has smaller ID, so that the election is accepted.
   */
  @Override
  public boolean sendElectionMessage(int currentId, String content) {
    List<Integer> candidateList = findElectionCandidateInstanceList(currentId);
    if (candidateList.isEmpty()) {
      return true;
    } else {
      Message electionMessage = new Message(MessageType.ELECTION_INVOKE, "");
      candidateList.stream()
          .forEach((i) -> instanceMap.get(i).onMessage(electionMessage));
      return false;
    }
  }

  /**
   * Send leader message to all the instances to notify the new leader.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the message is accepted.
   */
  @Override
  public boolean sendLeaderMessage(int currentId, int leaderId) {
    Message leaderMessage = new Message(MessageType.LEADER, String.valueOf(leaderId));
    instanceMap.keySet()
        .stream()
        .filter((i) -> i != currentId)
        .forEach((i) -> instanceMap.get(i).onMessage(leaderMessage));
    return false;
  }

  /**
   * Send heartbeat invoke message to the next instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  @Override
  public void sendHeartbeatInvokeMessage(int currentId) {
    Instance nextInstance = this.findNextInstance(currentId);
    Message heartbeatInvokeMessage = new Message(MessageType.HEARTBEAT_INVOKE, "");
    nextInstance.onMessage(heartbeatInvokeMessage);
  }

  /**
   * Find all the alive instances with smaller ID than current instance.
   *
   * @param currentId ID of current instance.
   * @return ID list of all the candidate instance.
   */
  private List<Integer> findElectionCandidateInstanceList(int currentId) {
    return instanceMap.keySet()
        .stream()
        .filter((i) -> i < currentId && instanceMap.get(i).isAlive())
        .collect(Collectors.toList());
  }

}
