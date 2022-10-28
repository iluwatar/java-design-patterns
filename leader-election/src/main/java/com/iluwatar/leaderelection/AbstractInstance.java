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

package com.iluwatar.leaderelection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class of all the instance implementation classes.
 */
@Slf4j
public abstract class AbstractInstance implements Instance, Runnable {

  protected static final int HEARTBEAT_INTERVAL = 5000;
  private static final String INSTANCE = "Instance ";

  protected MessageManager messageManager;
  protected Queue<Message> messageQueue;
  protected final int localId;
  protected int leaderId;
  protected boolean alive;

  /**
   * Constructor of BullyInstance.
   */
  public AbstractInstance(MessageManager messageManager, int localId, int leaderId) {
    this.messageManager = messageManager;
    this.messageQueue = new ConcurrentLinkedQueue<>();
    this.localId = localId;
    this.leaderId = leaderId;
    this.alive = true;
  }

  /**
   * The instance will execute the message in its message queue periodically once it is alive.
   */
  @Override
  @SuppressWarnings("squid:S2189")
  public void run() {
    while (true) {
      if (!this.messageQueue.isEmpty()) {
        this.processMessage(this.messageQueue.remove());
      }
    }
  }

  /**
   * Once messages are sent to the certain instance, it will firstly be added to the queue and wait
   * to be executed.
   *
   * @param message Message sent by other instances
   */
  @Override
  public void onMessage(Message message) {
    messageQueue.offer(message);
  }

  /**
   * Check if the instance is alive or not.
   *
   * @return {@code true} if the instance is alive.
   */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /**
   * Set the health status of the certain instance.
   *
   * @param alive {@code true} for alive.
   */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Process the message according to its type.
   *
   * @param message Message polled from queue.
   */
  private void processMessage(Message message) {
    switch (message.getType()) {
      case ELECTION:
        LOGGER.info(INSTANCE + localId + " - Election Message handling...");
        handleElectionMessage(message);
        break;
      case LEADER:
        LOGGER.info(INSTANCE + localId + " - Leader Message handling...");
        handleLeaderMessage(message);
        break;
      case HEARTBEAT:
        LOGGER.info(INSTANCE + localId + " - Heartbeat Message handling...");
        handleHeartbeatMessage(message);
        break;
      case ELECTION_INVOKE:
        LOGGER.info(INSTANCE + localId + " - Election Invoke Message handling...");
        handleElectionInvokeMessage();
        break;
      case LEADER_INVOKE:
        LOGGER.info(INSTANCE + localId + " - Leader Invoke Message handling...");
        handleLeaderInvokeMessage();
        break;
      case HEARTBEAT_INVOKE:
        LOGGER.info(INSTANCE + localId + " - Heartbeat Invoke Message handling...");
        handleHeartbeatInvokeMessage();
        break;
      default:
        break;
    }
  }

  /**
   * Abstract methods to handle different types of message. These methods need to be implemented in
   * concrete instance class to implement corresponding leader-selection pattern.
   */
  protected abstract void handleElectionMessage(Message message);

  protected abstract void handleElectionInvokeMessage();

  protected abstract void handleLeaderMessage(Message message);

  protected abstract void handleLeaderInvokeMessage();

  protected abstract void handleHeartbeatMessage(Message message);

  protected abstract void handleHeartbeatInvokeMessage();

}
