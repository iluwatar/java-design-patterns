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
package com.iluwatar.templatemethod

// ABOUTME: Abstract base test class for StealingMethod implementations.
// ABOUTME: Captures log output to verify pickTarget, confuseTarget, stealTheItem, and steal behavior.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/**
 * StealingMethodTest
 *
 * @param M Type of StealingMethod
 */
abstract class StealingMethodTest<M : StealingMethod>(
    private val method: M,
    private val expectedTarget: String,
    private val expectedTargetResult: String,
    private val expectedConfuseMethod: String,
    private val expectedStealMethod: String
) {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /** Verify if the thief picks the correct target */
    @Test
    fun testPickTarget() {
        assertEquals(expectedTarget, method.pickTarget())
    }

    /** Verify if the target confusing step goes as planned */
    @Test
    fun testConfuseTarget() {
        assertEquals(0, appender.logSize)

        method.confuseTarget(expectedTarget)
        assertEquals(expectedConfuseMethod, appender.lastMessage)
        assertEquals(1, appender.logSize)
    }

    /** Verify if the stealing step goes as planned */
    @Test
    fun testStealTheItem() {
        assertEquals(0, appender.logSize)

        method.stealTheItem(expectedTarget)
        assertEquals(expectedStealMethod, appender.lastMessage)
        assertEquals(1, appender.logSize)
    }

    /** Verify if the complete steal process goes as planned */
    @Test
    fun testSteal() {
        method.steal()

        assertTrue(appender.logContains(expectedTargetResult))
        assertTrue(appender.logContains(expectedConfuseMethod))
        assertTrue(appender.logContains(expectedStealMethod))
        assertEquals(3, appender.logSize)
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

        val logSize: Int
            get() = log.size

        val lastMessage: String
            get() = log[log.size - 1].formattedMessage

        fun logContains(message: String): Boolean =
            log.any { it.formattedMessage == message }
    }
}
