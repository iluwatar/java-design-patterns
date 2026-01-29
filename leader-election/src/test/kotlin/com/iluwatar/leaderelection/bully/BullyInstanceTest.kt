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

import com.iluwatar.leaderelection.Message
import com.iluwatar.leaderelection.MessageType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

// ABOUTME: Unit tests for the BullyInstance class.
// ABOUTME: Tests message handling and alive status functionality.

/**
 * BullyInstance unit test.
 */
class BullyInstanceTest {

    @Test
    fun testOnMessage() {
        val bullyInstance = BullyInstance(null, 1, 1)
        val bullyMessage = Message(MessageType.HEARTBEAT, "")
        bullyInstance.onMessage(bullyMessage)
        assertEquals(bullyMessage, bullyInstance.messageQueue.poll())
    }

    @Test
    fun testIsAlive() {
        val bullyInstance = BullyInstance(null, 1, 1)
        bullyInstance.alive = false
        assertFalse(bullyInstance.isAlive())
    }

    @Test
    fun testSetAlive() {
        val bullyInstance = BullyInstance(null, 1, 1)
        bullyInstance.setAlive(false)
        assertFalse(bullyInstance.isAlive())
    }
}
