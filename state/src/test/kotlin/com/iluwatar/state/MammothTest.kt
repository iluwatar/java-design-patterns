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
package com.iluwatar.state

// ABOUTME: Tests for Mammoth verifying state transitions and observed behavior.
// ABOUTME: Uses an InMemoryAppender to capture and assert log output through a full mood cycle.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/** MammothTest */
class MammothTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * Switch to a complete mammoth 'mood'-cycle and verify if the observed mood matches the expected
     * value.
     */
    @Test
    fun testTimePasses() {
        val mammoth = Mammoth()

        mammoth.observe()
        assertEquals("The mammoth is calm and peaceful.", appender.getLastMessage())
        assertEquals(1, appender.getLogSize())

        mammoth.timePasses()
        assertEquals("The mammoth gets angry!", appender.getLastMessage())
        assertEquals(2, appender.getLogSize())

        mammoth.observe()
        assertEquals("The mammoth is furious!", appender.getLastMessage())
        assertEquals(3, appender.getLogSize())

        mammoth.timePasses()
        assertEquals("The mammoth calms down.", appender.getLastMessage())
        assertEquals(4, appender.getLogSize())

        mammoth.observe()
        assertEquals("The mammoth is calm and peaceful.", appender.getLastMessage())
        assertEquals(5, appender.getLogSize())
    }

    /** Verify if [Mammoth.toString] gives the expected value */
    @Test
    fun testToString() {
        val toString = Mammoth().toString()
        assertNotNull(toString)
        assertEquals("The mammoth", toString)
    }

    private class InMemoryAppender : AppenderBase<ILoggingEvent>() {

        private val log = mutableListOf<ILoggingEvent>()

        init {
            (LoggerFactory.getLogger("root") as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        fun getLogSize(): Int = log.size

        fun getLastMessage(): String = log[log.size - 1].formattedMessage
    }
}
