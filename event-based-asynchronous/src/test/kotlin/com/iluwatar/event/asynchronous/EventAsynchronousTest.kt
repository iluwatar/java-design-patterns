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
package com.iluwatar.event.asynchronous

// ABOUTME: Comprehensive tests for the Event-based Asynchronous pattern implementation.
// ABOUTME: Tests async/sync events, event lifecycle, and exception scenarios.

import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration

/**
 * Application test
 */
class EventAsynchronousTest {

    @Test
    fun testAsynchronousEvent() {
        val eventManager = EventManager()
        val aEventId = eventManager.createAsync(Duration.ofSeconds(60))

        assertDoesNotThrow { eventManager.start(aEventId) }

        assertEquals(1, eventManager.eventPool.size)
        assertTrue(eventManager.eventPool.size < EventManager.MAX_RUNNING_EVENTS)
        assertEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent())

        assertDoesNotThrow { eventManager.cancel(aEventId) }
        assertTrue(eventManager.eventPool.isEmpty())
    }

    @Test
    fun testSynchronousEvent() {
        val eventManager = EventManager()
        val sEventId = eventManager.create(Duration.ofSeconds(60))

        assertDoesNotThrow { eventManager.start(sEventId) }
        assertEquals(1, eventManager.eventPool.size)
        assertTrue(eventManager.eventPool.size < EventManager.MAX_RUNNING_EVENTS)
        assertNotEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent())

        assertDoesNotThrow { eventManager.cancel(sEventId) }
        assertTrue(eventManager.eventPool.isEmpty())
    }

    @Test
    fun testFullSynchronousEvent() {
        val eventManager = EventManager()

        val eventTime = Duration.ofSeconds(1)

        val sEventId = eventManager.create(eventTime)
        assertEquals(1, eventManager.eventPool.size)

        eventManager.start(sEventId)

        await().until { eventManager.eventPool.isEmpty() }
    }

    @Test
    fun testUnsuccessfulSynchronousEvent() {
        assertThrows(InvalidOperationException::class.java) {
            val eventManager = EventManager()

            val sEventId = assertDoesNotThrow<Int> { eventManager.create(Duration.ofSeconds(60)) }
            eventManager.start(sEventId)
            val sEventId2 = eventManager.create(Duration.ofSeconds(60))
            eventManager.start(sEventId2)
        }
    }

    @Test
    fun testFullAsynchronousEvent() {
        val eventManager = EventManager()
        val eventTime = Duration.ofSeconds(1)

        val aEventId1 = assertDoesNotThrow<Int> { eventManager.createAsync(eventTime) }
        val aEventId2 = assertDoesNotThrow<Int> { eventManager.createAsync(eventTime) }
        val aEventId3 = assertDoesNotThrow<Int> { eventManager.createAsync(eventTime) }
        assertEquals(3, eventManager.eventPool.size)

        eventManager.start(aEventId1)
        eventManager.start(aEventId2)
        eventManager.start(aEventId3)

        await().until { eventManager.eventPool.isEmpty() }
    }

    @Test
    fun testLongRunningEventException() {
        assertThrows(LongRunningEventException::class.java) {
            val eventManager = EventManager()
            eventManager.createAsync(Duration.ofMinutes(31))
        }
    }

    @Test
    fun testMaxNumOfEventsAllowedException() {
        assertThrows(MaxNumOfEventsAllowedException::class.java) {
            val eventManager = EventManager()
            for (i in 0 until 1100) {
                eventManager.createAsync(Duration.ofSeconds(i.toLong()))
            }
        }
    }
}
