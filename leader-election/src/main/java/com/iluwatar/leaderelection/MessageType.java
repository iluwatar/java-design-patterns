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

/**
 * Message Type enum.
 */
public enum MessageType {

  /**
   * Start the election. The content of the message stores ID(s) of the candidate instance(s).
   */
  ELECTION,

  /**
   * Nodify the new leader. The content of the message should be the leader ID.
   */
  LEADER,

  /**
   * Check health of current leader instance.
   */
  HEARTBEAT,

  /**
   * Inform target instance to start election.
   */
  ELECTION_INVOKE,

  /**
   * Inform target instance to notify all the other instance that it is the new leader.
   */
  LEADER_INVOKE,

  /**
   * Inform target instance to start heartbeat.
   */
  HEARTBEAT_INVOKE

}

