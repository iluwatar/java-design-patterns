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
package com.iluwatar.leaderelection;

/**
 * MessageManager interface.
 */
public interface MessageManager {

  /**
   * Send heartbeat message to leader instance to check whether the leader instance is alive.
   *
   * @param leaderId Instance ID of leader instance.
   * @return {@code true} if leader instance is alive, or {@code false} if not.
   */
  boolean sendHeartbeatMessage(int leaderId);

  /**
   * Send election message to other instances.
   *
   * @param currentId Instance ID of which sends this message.
   * @param content   Election message content.
   * @return {@code true} if the message is accepted by the target instances.
   */
  boolean sendElectionMessage(int currentId, String content);

  /**
   * Send new leader notification message to other instances.
   *
   * @param currentId Instance ID of which sends this message.
   * @param leaderId  Leader message content.
   * @return {@code true} if the message is accepted by the target instances.
   */
  boolean sendLeaderMessage(int currentId, int leaderId);

  /**
   * Send heartbeat invoke message. This will invoke heartbeat task in the target instance.
   *
   * @param currentId Instance ID of which sends this message.
   */
  void sendHeartbeatInvokeMessage(int currentId);

}
