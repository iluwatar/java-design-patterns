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

import com.iluwatar.leaderelection.AbstractInstance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation with token ring algorithm. The instances in the system are organized as a ring.
 * Each instance should have a sequential id and the instance with smallest (or largest) id should
 * be the initial leader. All the other instances send heartbeat message to leader periodically to
 * check its health. If one certain instance finds the server done, it will send an election message
 * to the next alive instance in the ring, which contains its own ID. Then the next instance add its
 * ID into the message and pass it to the next. After all the alive instances' ID are add to the
 * message, the message is send back to the first instance, and it will choose the instance with the
 * smallest ID to be the new leader, and then send a leader message to other instances to inform the
 * result.
 */
@Slf4j
public class RingInstance extends AbstractInstance {
  private static final String INSTANCE = "Instance ";

  /**
   * Constructor of RingInstance.
   */
  public RingInstance(MessageManager messageManager, int localId, int leaderId) {
    super(messageManager, localId, leaderId);
  }

  /**
   * Process the heartbeat invoke message. After receiving the message, the instance will send a
   * heartbeat to leader to check its health. If alive, it will inform the next instance to do the
   * heartbeat. If not, it will start the election process.
   */
  @Override
  protected void handleHeartbeatInvokeMessage() {
    try {
      var isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderId);
      if (isLeaderAlive) {
        LOGGER.info(INSTANCE + localId + "- Leader is alive. Start next heartbeat in 5 second.");
        Thread.sleep(HEARTBEAT_INTERVAL);
        messageManager.sendHeartbeatInvokeMessage(this.localId);
      } else {
        LOGGER.info(INSTANCE + localId + "- Leader is not alive. Start election.");
        messageManager.sendElectionMessage(this.localId, String.valueOf(this.localId));
      }
    } catch (InterruptedException e) {
      LOGGER.info(INSTANCE + localId + "- Interrupted.");
    }
  }

  /**
   * Process election message. If the local ID is contained in the ID list, the instance will select
   * the alive instance with the smallest ID to be the new leader, and send the leader inform message.
   * If not, it will add its local ID to the list and send the message to the next instance in the
   * ring.
   */
  @Override
  protected void handleElectionMessage(Message message) {
    var content = message.getContent();
    LOGGER.info(INSTANCE + localId + " - Election Message: " + content);
    var candidateList = Arrays.stream(content.trim().split(","))
        .map(Integer::valueOf)
        .sorted()
        .toList();
    if (candidateList.contains(localId)) {
      var newLeaderId = candidateList.get(0);
      LOGGER.info(INSTANCE + localId + " - New leader should be " + newLeaderId + ".");
      messageManager.sendLeaderMessage(localId, newLeaderId);
    } else {
      content += "," + localId;
      messageManager.sendElectionMessage(localId, content);
    }
  }

  /**
   * Process leader Message. The instance will set the leader ID to be the new one and send the
   * message to the next instance until all the alive instance in the ring is informed.
   */
  @Override
  protected void handleLeaderMessage(Message message) {
    var newLeaderId = Integer.valueOf(message.getContent());
    if (this.leaderId != newLeaderId) {
      LOGGER.info(INSTANCE + localId + " - Update leaderID");
      this.leaderId = newLeaderId;
      messageManager.sendLeaderMessage(localId, newLeaderId);
    } else {
      LOGGER.info(INSTANCE + localId + " - Leader update done. Start heartbeat.");
      messageManager.sendHeartbeatInvokeMessage(localId);
    }
  }

  /**
   * Not used in Ring instance.
   */
  @Override
  protected void handleLeaderInvokeMessage() {
    // Not used in Ring instance.
  }

  @Override
  protected void handleHeartbeatMessage(Message message) {
    // Not used in Ring instance.
  }

  @Override
  protected void handleElectionInvokeMessage() {
    // Not used in Ring instance.
  }

}
