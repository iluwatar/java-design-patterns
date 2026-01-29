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

// ABOUTME: Test class for KingJoffrey event observer.
// ABOUTME: Verifies that received events are properly logged with correct messages.
package com.iluwatar.event.aggregator

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/**
 * KingJoffreyTest
 */
class KingJoffreyTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(KingJoffrey::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * Test if [KingJoffrey] tells us what event he received
     */
    @Test
    fun testOnEvent() {
        val kingJoffrey = KingJoffrey()

        Event.entries.forEachIndexed { i, event ->
            assertEquals(i, appender.logSize)
            kingJoffrey.onEvent(event)
            val expectedMessage = "Received event from the King's Hand: $event"
            assertEquals(expectedMessage, appender.lastMessage)
            assertEquals(i + 1, appender.logSize)
        }
    }

    private class InMemoryAppender(clazz: Class<*>) : AppenderBase<ILoggingEvent>() {
        private val log = mutableListOf<ILoggingEvent>()

        init {
            (LoggerFactory.getLogger(clazz) as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        val lastMessage: String
            get() = log[log.size - 1].formattedMessage

        val logSize: Int
            get() = log.size
    }
}
