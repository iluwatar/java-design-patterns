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

// ABOUTME: Tests for StatusMember verifying start/stop time recording.
// ABOUTME: Ensures StartingData and StoppingData are handled correctly and other data types are ignored.

package com.iluwatar.databus.members

import com.iluwatar.databus.DataBus
import com.iluwatar.databus.data.MessageData
import com.iluwatar.databus.data.StartingData
import com.iluwatar.databus.data.StoppingData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

/**
 * Tests for [StatusMember].
 */
internal class StatusMemberTest {

    @Test
    fun statusRecordsTheStartTime() {
        // given
        val startTime = LocalDateTime.of(2017, Month.APRIL, 1, 19, 9)
        val startingData = StartingData(startTime)
        val statusMember = StatusMember(1)
        // when
        statusMember.accept(startingData)
        // then
        assertEquals(startTime, statusMember.started)
    }

    @Test
    fun statusRecordsTheStopTime() {
        // given
        val stop = LocalDateTime.of(2017, Month.APRIL, 1, 19, 12)
        val stoppingData = StoppingData(stop)
        stoppingData.dataBus = DataBus.instance
        val statusMember = StatusMember(1)
        // when
        statusMember.accept(stoppingData)
        // then
        assertEquals(stop, statusMember.stopped)
    }

    @Test
    fun statusIgnoresMessageData() {
        // given
        val messageData = MessageData("message")
        val statusMember = StatusMember(1)
        // when
        statusMember.accept(messageData)
        // then
        assertNull(statusMember.started)
        assertNull(statusMember.stopped)
    }
}
