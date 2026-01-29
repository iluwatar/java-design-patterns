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

// ABOUTME: Tests for MessageCollectorMember verifying message collection behavior.
// ABOUTME: Ensures messages are collected from MessageData and other data types are ignored.

package com.iluwatar.databus.members

import com.iluwatar.databus.data.MessageData
import com.iluwatar.databus.data.StartingData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/**
 * Tests for [MessageCollectorMember].
 */
internal class MessageCollectorMemberTest {

    @Test
    fun collectMessageFromMessageData() {
        // given
        val message = "message"
        val messageData = MessageData(message)
        val collector = MessageCollectorMember("collector")
        // when
        collector.accept(messageData)
        // then
        assertTrue(collector.messages.contains(message))
    }

    @Test
    fun collectIgnoresMessageFromOtherDataTypes() {
        // given
        val startingData = StartingData(LocalDateTime.now())
        val collector = MessageCollectorMember("collector")
        // when
        collector.accept(startingData)
        // then
        assertEquals(0, collector.messages.size)
    }
}
