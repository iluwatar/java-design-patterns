/*
 * This project is licensed under the MIT license. 
 * Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.leaderelection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class for all instance implementations in the leader election system.
 * 
 * Each instance runs on its own thread and processes incoming messages asynchronously.
 * This version fixes the busy loop problem by adding a short sleep when no messages are present.
 */
@Slf4j
public abstract class AbstractInstance implements Instance, Runnable {

  // Interval between heartbeats in milliseconds.
  protected static final int HEARTBEAT_INTERVAL = 5000;
  private static final String INSTANCE = "Instance ";

  protected MessageManager messageManager;
  protected Queue<Message> messageQueue;
  protected final int localId;
  protected int leaderId;
  protected boolean alive;

  /**
   * Constructor initializing the instance.
   *
   * @param messageManager manager to send/receive messages
   * @param localId ID of this instance
   * @param leaderId current leader ID
   */
  public AbstractInstance(MessageManager messageManager, int localId, int leaderId) {
    this.messageManager = messageManager;
    this.messageQueue = new ConcurrentLinkedQueue<>();
    this.localId = localId;
    this.leaderId = leaderId;
    this.alive = true;
  }

  /**
   * Thread run loop — continuously processes messages while instance is alive.
   * 
   * 🟢 FIXED: Added small sleep when queue is empty to avoid busy looping.
   */
  @Override
  public void run() {
    while (alive) {
      if (!this.messageQueue.isEmpty()) {
        processMessage(this.messageQueue.poll());
      } else {
        try {
          Thread.sleep(100); // 🔸 Prevents busy loop CPU overuse
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          LOGGER.warn(INSTANCE + localId + " thread interrupted.");
          break;
        }
      }
    }
    LOGGER.info(INSTANCE + localId + " stopped running.");
  }

  /**
   * Add a new message to the queue to be processed.
   *
   * @param message Message sent by other instances
   */
  @Override
  public void onMessage(Message message) {
    messageQueue.offer(message);
  }

  /** Check if this instance is alive. */
  @Override
  public boolean isAlive() {
    return alive;
  }

  /** Update the alive status of this instance. */
  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Process the given message according to its type.
   *
   * @param message message to process
   */
  private void processMessage(Message message) {
    switch (message.getType()) {
      case ELECTION -> {
        LOGGER.info("{}{} - Handling Election Message...", INSTANCE, localId);
        handleElectionMessage(message);
      }
      case LEADER -> {
        LOGGER.info("{}{} - Handling Leader Message...", INSTANCE, localId);
        handleLeaderMessage(message);
      }
      case HEARTBEAT -> {
        LOGGER.info("{}{} - Handling Heartbeat Message...", INSTANCE, localId);
        handleHeartbeatMessage(message);
      }
      case ELECTION_INVOKE -> {
        LOGGER.info("{}{} - Handling Election Invoke...", INSTANCE, localId);
        handleElectionInvokeMessage();
      }
      case LEADER_INVOKE -> {
        LOGGER.info("{}{} - Handling Leader Invoke...", INSTANCE, localId);
        handleLeaderInvokeMessage();
      }
      case HEARTBEAT_INVOKE -> {
        LOGGER.info("{}{} - Handling Heartbeat Invoke...", INSTANCE, localId);
        handleHeartbeatInvokeMessage();
      }
      default -> LOGGER.warn("{}{} - Unknown message type received.", INSTANCE, localId);
    }
  }

  // Abstract methods for handling various message types — to be implemented by subclasses.
  protected abstract void handleElectionMessage(Message message);
  protected abstract void handleElectionInvokeMessage();
  protected abstract void handleLeaderMessage(Message message);
  protected abstract void handleLeaderInvokeMessage();
  protected abstract void handleHeartbeatMessage(Message message);
  protected abstract void handleHeartbeatInvokeMessage();
}
