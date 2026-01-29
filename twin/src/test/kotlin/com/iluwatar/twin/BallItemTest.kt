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
package com.iluwatar.twin

// ABOUTME: Tests for BallItem verifying click toggling, draw delegation, and move logging.
// ABOUTME: Uses MockK for mocking BallThread and InMemoryAppender for log verification.

import com.iluwatar.twin.utils.InMemoryAppender
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** BallItemTest */
class BallItemTest {

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
    fun testClick() {
        val ballThread = mockk<BallThread>(relaxed = true)
        val ballItem = BallItem()
        ballItem.twin = ballThread

        repeat(10) {
            ballItem.click()
            ballItem.click()
        }

        verifyOrder {
            repeat(10) {
                ballThread.suspendMe()
                ballThread.resumeMe()
            }
        }

        excludeRecords { ballThread.equals(any()) }
        confirmVerified(ballThread)
    }

    @Test
    fun testDoDraw() {
        val ballItem = BallItem()
        val ballThread = mockk<BallThread>(relaxed = true)
        ballItem.twin = ballThread

        ballItem.draw()
        assertTrue(appender.logContains("draw"))
        assertTrue(appender.logContains("doDraw"))

        excludeRecords { ballThread.equals(any()) }
        confirmVerified(ballThread)
        assertEquals(2, appender.getLogSize())
    }

    @Test
    fun testMove() {
        val ballItem = BallItem()
        val ballThread = mockk<BallThread>(relaxed = true)
        ballItem.twin = ballThread

        ballItem.move()
        assertTrue(appender.logContains("move"))

        excludeRecords { ballThread.equals(any()) }
        confirmVerified(ballThread)
        assertEquals(1, appender.getLogSize())
    }
}
