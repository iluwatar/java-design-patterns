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
package com.iluwatar.poison.pill

// ABOUTME: Test class for the Producer component.
// ABOUTME: Verifies message sending, stop behavior, and POISON_PILL delivery.

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.mockk.confirmVerified
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

/** ProducerTest */
class ProducerTest {

    @Test
    fun testSend() {
        val publishPoint = mockk<MqPublishPoint>(relaxed = true)
        val producer = Producer("producer", publishPoint)
        confirmVerified(publishPoint)

        producer.send("Hello!")

        val messageSlot = slot<Message>()
        verify { publishPoint.put(capture(messageSlot)) }

        val message = messageSlot.captured
        assertNotNull(message)
        assertEquals("producer", message.getHeader(Message.Headers.SENDER))
        assertNotNull(message.getHeader(Message.Headers.DATE))
        assertEquals("Hello!", message.getBody())

        confirmVerified(publishPoint)
    }

    @Test
    fun testStop() {
        val publishPoint = mockk<MqPublishPoint>(relaxed = true)
        val producer = Producer("producer", publishPoint)
        confirmVerified(publishPoint)

        producer.stop()
        verify { publishPoint.put(eq(Message.POISON_PILL)) }

        try {
            producer.send("Hello!")
            fail("Expected 'IllegalStateException' at this point, since the producer has stopped!")
        } catch (e: IllegalStateException) {
            assertNotNull(e)
            assertNotNull(e.message)
            assertEquals(
                "Producer Hello! was stopped and fail to deliver requested message [producer].",
                e.message
            )
        }

        confirmVerified(publishPoint)
    }
}
