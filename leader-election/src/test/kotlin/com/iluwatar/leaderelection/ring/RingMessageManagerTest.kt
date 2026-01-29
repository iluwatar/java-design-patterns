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
package com.iluwatar.leaderelection.ring

import com.iluwatar.leaderelection.Instance
import com.iluwatar.leaderelection.Message
import com.iluwatar.leaderelection.MessageType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

// ABOUTME: Unit tests for the RingMessageManager class.
// ABOUTME: Tests heartbeat, election, and leader message sending behavior.

/**
 * RingMessageManager unit test.
 */
class RingMessageManagerTest {

    @Test
    fun testSendHeartbeatMessage() {
        val instance1 = RingInstance(null, 1, 1)
        val instanceMap = mapOf<Int, Instance>(1 to instance1)
        val messageManager = RingMessageManager(instanceMap)
        assertTrue(messageManager.sendHeartbeatMessage(1))
    }

    @Test
    fun testSendElectionMessage() {
        val instance1 = RingInstance(null, 1, 1)
        val instance2 = RingInstance(null, 2, 1)
        val instance3 = RingInstance(null, 3, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3
        )
        val messageManager = RingMessageManager(instanceMap)
        val messageContent = "2"
        messageManager.sendElectionMessage(2, messageContent)
        val ringMessage = Message(MessageType.ELECTION, messageContent)
        val ringMessageSent = instance3.messageQueue.poll()
        assertEquals(ringMessage.type, ringMessageSent?.type)
        assertEquals(ringMessage.content, ringMessageSent?.content)
    }

    @Test
    fun testSendLeaderMessage() {
        val instance1 = RingInstance(null, 1, 1)
        val instance2 = RingInstance(null, 2, 1)
        val instance3 = RingInstance(null, 3, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3
        )
        val messageManager = RingMessageManager(instanceMap)
        val messageContent = "3"
        messageManager.sendLeaderMessage(2, 3)
        val ringMessage = Message(MessageType.LEADER, messageContent)
        val ringMessageSent = instance3.messageQueue.poll()
        assertEquals(ringMessage, ringMessageSent)
    }

    @Test
    fun testSendHeartbeatInvokeMessage() {
        val instance1 = RingInstance(null, 1, 1)
        val instance2 = RingInstance(null, 2, 1)
        val instance3 = RingInstance(null, 3, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3
        )
        val messageManager = RingMessageManager(instanceMap)
        messageManager.sendHeartbeatInvokeMessage(2)
        val ringMessage = Message(MessageType.HEARTBEAT_INVOKE, "")
        val ringMessageSent = instance3.messageQueue.poll()
        assertEquals(ringMessage.type, ringMessageSent?.type)
        assertEquals(ringMessage.content, ringMessageSent?.content)
    }
}
