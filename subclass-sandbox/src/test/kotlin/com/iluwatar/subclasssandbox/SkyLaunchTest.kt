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
package com.iluwatar.subclasssandbox

// ABOUTME: Tests for the SkyLaunch superpower verifying move, playSound, spawnParticles, and activate.
// ABOUTME: Uses a Logback InMemoryAppender to capture and assert log output from sandbox methods.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.slf4j.LoggerFactory

/** SkyLaunch unit tests. */
class SkyLaunchTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    @Test
    fun testMove() {
        val skyLaunch = SkyLaunch()
        skyLaunch.move(1.0, 1.0, 1.0)
        assertEquals("Move to ( 1.0, 1.0, 1.0 )", appender.lastMessage)
    }

    @Test
    fun testPlaySound() {
        val skyLaunch = SkyLaunch()
        skyLaunch.playSound("SOUND_NAME", 1)
        assertEquals("Play SOUND_NAME with volume 1", appender.lastMessage)
    }

    @Test
    fun testSpawnParticles() {
        val skyLaunch = SkyLaunch()
        skyLaunch.spawnParticles("PARTICLE_TYPE", 100)
        assertEquals("Spawn 100 particle with type PARTICLE_TYPE", appender.lastMessage)
    }

    @Test
    fun testActivate() {
        val skyLaunch = SkyLaunch()
        skyLaunch.activate()
        val messages = appender.messages
        assertEquals(3, messages.size)
        assertEquals("Move to ( 0.0, 0.0, 20.0 )", messages[0])
        assertEquals("Play SKYLAUNCH_SOUND with volume 1", messages[1])
        assertEquals("Spawn 100 particle with type SKYLAUNCH_PARTICLE", messages[2])
    }

    private class InMemoryAppender : AppenderBase<ILoggingEvent>() {
        private val log = mutableListOf<ILoggingEvent>()

        init {
            (LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        val messages: List<String>
            get() = log.map { it.formattedMessage }

        val lastMessage: String
            get() = log.last().formattedMessage
    }
}
