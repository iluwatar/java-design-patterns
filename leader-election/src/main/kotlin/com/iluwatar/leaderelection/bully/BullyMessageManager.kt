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
package com.iluwatar.leaderelection.bully

import com.iluwatar.leaderelection.AbstractMessageManager
import com.iluwatar.leaderelection.Instance
import com.iluwatar.leaderelection.Message
import com.iluwatar.leaderelection.MessageType

// ABOUTME: Message manager implementation for the Bully leader election algorithm.
// ABOUTME: Handles sending election messages to instances with higher IDs and leader notifications.

/**
 * Implementation of BullyMessageManager.
 */
class BullyMessageManager(
    instanceMap: Map<Int, Instance>
) : AbstractMessageManager(instanceMap) {

    /**
     * Send heartbeat message to current leader instance to check the health.
     *
     * @param leaderId leaderID
     * @return `true` if the leader is alive.
     */
    override fun sendHeartbeatMessage(leaderId: Int): Boolean {
        val leaderInstance = instanceMap[leaderId]
        return leaderInstance!!.isAlive()
    }

    /**
     * Send election message to all the instances with smaller ID.
     *
     * @param currentId Instance ID of which sends this message.
     * @param content Election message content.
     * @return `true` if no alive instance has smaller ID, so that the election is accepted.
     */
    override fun sendElectionMessage(currentId: Int, content: String): Boolean {
        val candidateList = findElectionCandidateInstanceList(currentId)
        return if (candidateList.isEmpty()) {
            true
        } else {
            val electionMessage = Message(MessageType.ELECTION_INVOKE, "")
            candidateList.forEach { instanceMap[it]!!.onMessage(electionMessage) }
            false
        }
    }

    /**
     * Send leader message to all the instances to notify the new leader.
     *
     * @param currentId Instance ID of which sends this message.
     * @param leaderId Leader message content.
     * @return `true` if the message is accepted.
     */
    override fun sendLeaderMessage(currentId: Int, leaderId: Int): Boolean {
        val leaderMessage = Message(MessageType.LEADER, leaderId.toString())
        instanceMap.keys
            .filter { it != currentId }
            .forEach { instanceMap[it]!!.onMessage(leaderMessage) }
        return false
    }

    /**
     * Send heartbeat invoke message to the next instance.
     *
     * @param currentId Instance ID of which sends this message.
     */
    override fun sendHeartbeatInvokeMessage(currentId: Int) {
        val nextInstance = findNextInstance(currentId)
        val heartbeatInvokeMessage = Message(MessageType.HEARTBEAT_INVOKE, "")
        nextInstance.onMessage(heartbeatInvokeMessage)
    }

    /**
     * Find all the alive instances with smaller ID than current instance.
     *
     * @param currentId ID of current instance.
     * @return ID list of all the candidate instance.
     */
    private fun findElectionCandidateInstanceList(currentId: Int): List<Int> {
        return instanceMap.keys
            .filter { it < currentId && instanceMap[it]!!.isAlive() }
    }
}
