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
package com.iluwatar.strategy

// ABOUTME: Parameterized tests verifying each strategy implementation logs the correct message.
// ABOUTME: Uses a Logback InMemoryAppender to capture and assert log output.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory

/** DragonSlayingStrategyTest */
class DragonSlayingStrategyTest {

    companion object {
        /** Assembles test parameters. */
        @JvmStatic
        fun dataProvider(): Collection<Array<Any>> = listOf(
            arrayOf(MeleeStrategy(), "With your Excalibur you sever the dragon's head!"),
            arrayOf(
                ProjectileStrategy(),
                "You shoot the dragon with the magical crossbow and it falls dead on the ground!",
            ),
            arrayOf(
                SpellStrategy(),
                "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!",
            ),
        )
    }

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /** Test if executing the strategy gives the correct response. */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testExecute(strategy: DragonSlayingStrategy, expectedResult: String) {
        strategy.execute()
        assertEquals(expectedResult, appender.lastMessage)
        assertEquals(1, appender.logSize)
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
    }
}
