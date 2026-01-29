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
// ABOUTME: Unit tests for LogAggregator verifying log collection, filtering, and buffer flushing.
// ABOUTME: Uses MockK to mock CentralLogStore and verify correct interactions.
package com.iluwatar.logaggregation

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class LogAggregatorTest {
    private lateinit var centralLogStore: CentralLogStore
    private lateinit var logAggregator: LogAggregator

    @BeforeEach
    fun setUp() {
        centralLogStore = mockk(relaxed = true)
        logAggregator = LogAggregator(centralLogStore, LogLevel.INFO)
    }

    @Test
    fun whenThreeInfoLogsAreCollected_thenCentralLogStoreShouldStoreAllOfThem() {
        logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 1"))
        logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 2"))

        verifyNoInteractionsWithCentralLogStore()

        logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 3"))

        verifyCentralLogStoreInvokedTimes(3)
    }

    @Test
    fun whenDebugLogIsCollected_thenNoLogsShouldBeStored() {
        logAggregator.collectLog(createLogEntry(LogLevel.DEBUG, "Sample debug log message"))

        verifyNoInteractionsWithCentralLogStore()
    }

    private fun createLogEntry(
        logLevel: LogLevel,
        message: String,
    ): LogEntry = LogEntry("ServiceA", logLevel, message, LocalDateTime.now())

    private fun verifyNoInteractionsWithCentralLogStore() {
        verify(exactly = 0) { centralLogStore.storeLog(any()) }
    }

    private fun verifyCentralLogStoreInvokedTimes(times: Int) {
        verify(exactly = times) { centralLogStore.storeLog(any()) }
    }
}
