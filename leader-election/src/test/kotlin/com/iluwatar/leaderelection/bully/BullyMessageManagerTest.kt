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

import com.iluwatar.leaderelection.Instance
import com.iluwatar.leaderelection.Message
import com.iluwatar.leaderelection.MessageType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

// ABOUTME: Unit tests for the BullyMessageManager class.
// ABOUTME: Tests heartbeat, election, and leader message sending behavior.

/**
 * BullyMessageManager unit test.
 */
class BullyMessageManagerTest {

    @Test
    fun testSendHeartbeatMessage() {
        val instance1 = BullyInstance(null, 1, 1)
        val instanceMap = mapOf<Int, Instance>(1 to instance1)
        val messageManager = BullyMessageManager(instanceMap)
        assertTrue(messageManager.sendHeartbeatMessage(1))
    }

    @Test
    fun testSendElectionMessageNotAccepted() {
        val instance1 = BullyInstance(null, 1, 1)
        val instance2 = BullyInstance(null, 2, 1)
        val instance3 = BullyInstance(null, 3, 1)
        val instance4 = BullyInstance(null, 4, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3,
            4 to instance4
        )
        instance1.setAlive(false)
        val messageManager = BullyMessageManager(instanceMap)
        val result = messageManager.sendElectionMessage(3, "3")
        val message2 = instance2.messageQueue.poll()
        val instance4QueueSize = instance4.messageQueue.size
        val expectedMessage = Message(MessageType.ELECTION_INVOKE, "")
        assertEquals(expectedMessage, message2)
        assertEquals(0, instance4QueueSize)
        assertFalse(result)
    }

    @Test
    fun testElectionMessageAccepted() {
        val instance1 = BullyInstance(null, 1, 1)
        val instance2 = BullyInstance(null, 2, 1)
        val instance3 = BullyInstance(null, 3, 1)
        val instance4 = BullyInstance(null, 4, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3,
            4 to instance4
        )
        instance1.setAlive(false)
        val messageManager = BullyMessageManager(instanceMap)
        val result = messageManager.sendElectionMessage(2, "2")
        assertTrue(result)
    }

    @Test
    fun testSendLeaderMessage() {
        val instance1 = BullyInstance(null, 1, 1)
        val instance2 = BullyInstance(null, 2, 1)
        val instance3 = BullyInstance(null, 3, 1)
        val instance4 = BullyInstance(null, 4, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3,
            4 to instance4
        )
        instance1.setAlive(false)
        val messageManager = BullyMessageManager(instanceMap)
        messageManager.sendLeaderMessage(2, 2)
        val message3 = instance3.messageQueue.poll()
        val message4 = instance4.messageQueue.poll()
        val expectedMessage = Message(MessageType.LEADER, "2")
        assertEquals(expectedMessage, message3)
        assertEquals(expectedMessage, message4)
    }

    @Test
    fun testSendHeartbeatInvokeMessage() {
        val instance1 = BullyInstance(null, 1, 1)
        val instance2 = BullyInstance(null, 2, 1)
        val instance3 = BullyInstance(null, 3, 1)
        val instanceMap = mapOf<Int, Instance>(
            1 to instance1,
            2 to instance2,
            3 to instance3
        )
        val messageManager = BullyMessageManager(instanceMap)
        messageManager.sendHeartbeatInvokeMessage(2)
        val message = Message(MessageType.HEARTBEAT_INVOKE, "")
        val messageSent = instance3.messageQueue.poll()
        assertEquals(message.type, messageSent?.type)
        assertEquals(message.content, messageSent?.content)
    }
}
