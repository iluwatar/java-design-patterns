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
package com.iluwatar.circuitbreaker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// ABOUTME: Unit tests for MonitoringService verifying local and remote service call handling.
// ABOUTME: Tests both successful and failed remote service scenarios through circuit breakers.

/**
 * Monitoring Service test
 */
class MonitoringServiceTest {
    // long timeout, int failureThreshold, long retryTimePeriod
    @Test
    fun testLocalResponse() {
        val monitoringService = MonitoringService(null, null)
        val response = monitoringService.localResourceResponse()
        assertEquals(response, "Local Service is working")
    }

    @Test
    fun testDelayedRemoteResponseSuccess() {
        val delayedService = DelayedRemoteService(System.nanoTime() - 2L * 1000 * 1000 * 1000, 2)
        val delayedServiceCircuitBreaker =
            DefaultCircuitBreaker(delayedService, 3000, 1, 2L * 1000 * 1000 * 1000)

        val monitoringService = MonitoringService(delayedServiceCircuitBreaker, null)
        // Set time in past to make the server work
        val response = monitoringService.delayedServiceResponse()
        assertEquals(response, "Delayed service is working")
    }

    @Test
    fun testDelayedRemoteResponseFailure() {
        val delayedService = DelayedRemoteService(System.nanoTime(), 2)
        val delayedServiceCircuitBreaker =
            DefaultCircuitBreaker(delayedService, 3000, 1, 2L * 1000 * 1000 * 1000)
        val monitoringService = MonitoringService(delayedServiceCircuitBreaker, null)
        // Set time as current time as initially server fails
        val response = monitoringService.delayedServiceResponse()
        assertEquals(response, "Delayed service is down")
    }

    @Test
    fun testQuickRemoteServiceResponse() {
        val delayedService = QuickRemoteService()
        val delayedServiceCircuitBreaker =
            DefaultCircuitBreaker(delayedService, 3000, 1, 2L * 1000 * 1000 * 1000)
        val monitoringService = MonitoringService(delayedServiceCircuitBreaker, null)
        // Set time as current time as initially server fails
        val response = monitoringService.delayedServiceResponse()
        assertEquals(response, "Quick Service is working")
    }
}