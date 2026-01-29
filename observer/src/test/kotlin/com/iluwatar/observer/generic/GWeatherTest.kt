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
package com.iluwatar.observer.generic

// ABOUTME: Tests for the GenWeather generic subject class verifying observer notification behavior.
// ABOUTME: Uses MockK to verify add/remove observer and sequential weather change notifications.

import com.iluwatar.observer.WeatherType
import com.iluwatar.observer.utils.InMemoryAppender
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** GWeatherTest */
class GWeatherTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(GenWeather::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * Add a [Race] observer, verify if it gets notified of a weather change, remove the
     * observer again and verify that there are no more notifications.
     */
    @Test
    fun testAddRemoveObserver() {
        val observer = mockk<Race>(relaxed = true)
        excludeRecords { observer.equals(any()) }
        excludeRecords { observer.hashCode() }

        val weather = GenWeather()
        weather.addObserver(observer)
        confirmVerified(observer)

        weather.timePasses()
        assertEquals("The weather changed to rainy.", appender.getLastMessage())
        verify { observer.update(weather, WeatherType.RAINY) }

        weather.removeObserver(observer)
        weather.timePasses()
        assertEquals("The weather changed to windy.", appender.getLastMessage())

        confirmVerified(observer)
        assertEquals(2, appender.getLogSize())
    }

    /** Verify if the weather passes in the order of the [WeatherType]s */
    @Test
    fun testTimePasses() {
        val observer = mockk<Race>(relaxed = true)
        val weather = GenWeather()
        weather.addObserver(observer)

        val weatherTypes = WeatherType.entries.toTypedArray()
        for (i in 1 until 20) {
            weather.timePasses()
        }

        verifyOrder {
            for (i in 1 until 20) {
                observer.update(weather, weatherTypes[i % weatherTypes.size])
            }
        }

        confirmVerified(observer)
    }
}
