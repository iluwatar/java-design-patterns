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

// ABOUTME: Tests for AdvancedSorceress that validates setter-based dependency injection.
// ABOUTME: Verifies that AdvancedSorceress can smoke any Tobacco set via property.
package com.iluwatar.dependency.injection

import com.iluwatar.dependency.injection.utils.InMemoryAppender
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** AdvancedSorceressTest */
internal class AdvancedSorceressTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(Tobacco::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * Test if the [AdvancedSorceress] smokes whatever instance of [Tobacco] is passed to
     * her through the setter's parameter
     */
    @Test
    fun testSmokeEveryThing() {
        val tobaccos = listOf(OldTobyTobacco(), RivendellTobacco(), SecondBreakfastTobacco())

        // Verify if the sorceress is smoking the correct tobacco ...
        tobaccos.forEach { tobacco ->
            val advancedSorceress = AdvancedSorceress()
            advancedSorceress.tobacco = tobacco
            advancedSorceress.smoke()
            val lastMessage = appender.getLastMessage()
            assertEquals("AdvancedSorceress smoking ${tobacco.javaClass.simpleName}", lastMessage)
        }

        // ... and nothing else is happening.
        assertEquals(tobaccos.size, appender.getLogSize())
    }
}
