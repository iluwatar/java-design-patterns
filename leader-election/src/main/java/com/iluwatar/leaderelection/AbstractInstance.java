package com.iluwatar.leaderelection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

public abstract class AbstractInstance implements Instance, Runnable {

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
  public void run() {
    while (true) {
      if (!this.messageQueue.isEmpty()) {
        this.processMessage(this.messageQueue.remove());
      }
      System.out.flush();
    }
  }

  /**
   * Message listening method of the instance.
   */
  @Override
  public void onMessage(Message message) {
    messageQueue.offer(message);
  }

  /**
   * Check whether the certain instnace is alive or not.
   * @return {@code true} if the instance is alive
   */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /**
   * Set the status of instance.
   */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  private void processMessage(Message message) {
    switch (message.getType()) {
      case ELECTION:
        System.out.println("Instance " + localId + " - Election Message handling...");
        handleElectionMessage(message);
        break;
      case LEADER:
        System.out.println("Instance " + localId + " - Leader Message handling...");
        handleLeaderMessage(message);
        break;
      case HEARTBEAT:
        System.out.println("Instance " + localId + " - Heartbeat Message handling...");
        handleHeartbeatMessage(message);
        break;
      case ELECTION_INVODE:
        System.out.println("Instance " + localId + " - Election Invoke Message handling...");
        handleElectionInvokeMessage();
        break;
      case LEADER_INVOKE:
        System.out.println("Instance " + localId + " - Leader Invoke Message handling...");
        handleLeaderInvokeMessage();
        break;
      case HEARTBEAT_INVOKE:
        System.out.println("Instance " + localId + " - Heartbeat Invoke Message handling...");
        handleHeartbeatInvokeMessage();
        break;
      default:
        break;
    }
  }

  protected abstract void handleElectionMessage(Message message);

  protected abstract void handleElectionInvokeMessage();

  protected abstract void handleLeaderMessage(Message message);

  protected abstract void handleLeaderInvokeMessage();

  protected abstract void handleHeartbeatMessage(Message message);

  protected abstract void handleHeartbeatInvokeMessage();

}
