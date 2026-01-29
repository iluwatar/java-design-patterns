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

// ABOUTME: Abstract test class for EventEmitter implementations.
// ABOUTME: Provides common test logic for verifying event emission on specific days.
package com.iluwatar.event.aggregator

import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * Tests for Event Emitter
 *
 * @param E Type of Event Emitter
 */
abstract class EventEmitterTest<E : EventEmitter>(
    private val specialDay: Weekday?,
    private val event: Event?,
    private val factoryWithDefaultObserver: (EventObserver, Event) -> E,
    private val factoryWithoutDefaultObserver: () -> E
) {

    /**
     * Go over every day of the month, and check if the event is emitted on the given day. This test
     * is executed twice, once without a default emitter and once with
     */
    @Test
    fun testAllDays() {
        testAllDaysWithoutDefaultObserver(specialDay, event)
        testAllDaysWithDefaultObserver(specialDay, event)
    }

    /**
     * Pass each week of the day, day by day to the event emitter and verify of the given observers
     * received the correct event on the special day.
     *
     * @param specialDay The special day on which an event is emitted
     * @param event The expected event emitted by the test object
     * @param emitter The event emitter
     * @param observers The registered observer mocks
     */
    private fun testAllDays(
        specialDay: Weekday?,
        event: Event?,
        emitter: E,
        vararg observers: EventObserver
    ) {
        var expectedCalls = 0

        for (weekday in Weekday.entries) {
            // Pass each week of the day, day by day to the event emitter
            emitter.timePasses(weekday)

            if (weekday == specialDay && event != null) {
                expectedCalls++
                // On a special day, every observer should have received the event
                for (observer in observers) {
                    verify(exactly = expectedCalls) { observer.onEvent(event) }
                }
            } else {
                // On any other normal day, the observers should have received nothing new
                for (observer in observers) {
                    verify(exactly = expectedCalls) { observer.onEvent(any()) }
                }
            }
        }

        // The observers should not have received any additional events after the week
        for (observer in observers) {
            verify(exactly = expectedCalls) { observer.onEvent(any()) }
        }
    }

    /**
     * Go over every day of the month, and check if the event is emitted on the given day. Use an
     * event emitter without a default observer
     *
     * @param specialDay The special day on which an event is emitted
     * @param event The expected event emitted by the test object
     */
    private fun testAllDaysWithoutDefaultObserver(specialDay: Weekday?, event: Event?) {
        val observer1 = mockk<EventObserver>(relaxed = true)
        val observer2 = mockk<EventObserver>(relaxed = true)

        val emitter = factoryWithoutDefaultObserver()
        if (event != null) {
            emitter.registerObserver(observer1, event)
            emitter.registerObserver(observer2, event)
        }

        testAllDays(specialDay, event, emitter, observer1, observer2)
    }

    /**
     * Go over every day of the month, and check if the event is emitted on the given day.
     *
     * @param specialDay The special day on which an event is emitted
     * @param event The expected event emitted by the test object
     */
    private fun testAllDaysWithDefaultObserver(specialDay: Weekday?, event: Event?) {
        val defaultObserver = mockk<EventObserver>(relaxed = true)
        val observer1 = mockk<EventObserver>(relaxed = true)
        val observer2 = mockk<EventObserver>(relaxed = true)

        if (event != null) {
            val emitter = factoryWithDefaultObserver(defaultObserver, event)
            emitter.registerObserver(observer1, event)
            emitter.registerObserver(observer2, event)

            testAllDays(specialDay, event, emitter, defaultObserver, observer1, observer2)
        }
    }
}
