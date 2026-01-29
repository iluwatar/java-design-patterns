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

// ABOUTME: Tests for BallThread verifying suspend, resume, and interrupt behavior.
// ABOUTME: Uses MockK for mocking BallItem and verifies thread lifecycle operations.

import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Test
import java.time.Duration.ofMillis

/** BallThreadTest */
class BallThreadTest {

    /** Verify if the [BallThread] can be suspended */
    @Test
    fun testSuspend() {
        assertTimeout(ofMillis(5000)) {
            val ballThread = BallThread()

            val ballItem = mockk<BallItem>(relaxed = true)
            ballThread.twin = ballItem

            ballThread.start()
            Thread.sleep(200)
            verify(atLeast = 1) { ballItem.draw() }
            verify(atLeast = 1) { ballItem.move() }
            ballThread.suspendMe()

            Thread.sleep(1000)

            ballThread.stopMe()
            ballThread.join()

            excludeRecords { ballItem.equals(any()) }
            confirmVerified(ballItem)
        }
    }

    /** Verify if the [BallThread] can be resumed */
    @Test
    fun testResume() {
        assertTimeout(ofMillis(5000)) {
            val ballThread = BallThread()

            val ballItem = mockk<BallItem>(relaxed = true)
            ballThread.twin = ballItem

            ballThread.suspendMe()
            ballThread.start()

            Thread.sleep(1000)

            excludeRecords { ballItem.equals(any()) }
            confirmVerified(ballItem)

            ballThread.resumeMe()
            Thread.sleep(300)
            verify(atLeast = 1) { ballItem.draw() }
            verify(atLeast = 1) { ballItem.move() }

            ballThread.stopMe()
            ballThread.join()

            excludeRecords { ballItem.equals(any()) }
            confirmVerified(ballItem)
        }
    }

    /** Verify if the [BallThread] is interruptible */
    @Test
    fun testInterrupt() {
        assertTimeout(ofMillis(5000)) {
            val ballThread = BallThread()
            val exceptionHandler = mockk<Thread.UncaughtExceptionHandler>(relaxed = true)
            ballThread.uncaughtExceptionHandler = exceptionHandler
            ballThread.twin = mockk<BallItem>(relaxed = true)
            ballThread.start()
            ballThread.interrupt()
            ballThread.join()

            verify(exactly = 1) {
                exceptionHandler.uncaughtException(ballThread, any<RuntimeException>())
            }
            excludeRecords { exceptionHandler.equals(any()) }
            confirmVerified(exceptionHandler)
        }
    }
}
