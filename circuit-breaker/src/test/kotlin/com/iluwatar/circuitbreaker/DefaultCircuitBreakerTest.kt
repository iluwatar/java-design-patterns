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

// ABOUTME: Unit tests for DefaultCircuitBreaker verifying state evaluation and transitions.
// ABOUTME: Tests failure counting, state bypass, and successful API response handling.

/**
 * Circuit Breaker test
 */
class DefaultCircuitBreakerTest {
    // long timeout, int failureThreshold, long retryTimePeriod
    @Test
    fun testEvaluateState() {
        val circuitBreaker = DefaultCircuitBreaker(null, 1, 1, 100)
        // Right now, failureCount<failureThreshold, so state should be closed
        assertEquals(circuitBreaker.getState(), "CLOSED")
        circuitBreaker.failureCount = 4
        circuitBreaker.lastFailureTime = System.nanoTime()
        circuitBreaker.evaluateState()
        // Since failureCount>failureThreshold, and lastFailureTime is nearly equal to current time,
        // state should be half-open
        assertEquals(circuitBreaker.getState(), "HALF_OPEN")
        // Since failureCount>failureThreshold, and lastFailureTime is much lesser current time,
        // state should be open
        // Note: Using Int multiplication to match Java behavior with overflow
        circuitBreaker.lastFailureTime = System.nanoTime() - (1000 * 1000 * 1000 * 1000)
        circuitBreaker.evaluateState()
        assertEquals(circuitBreaker.getState(), "OPEN")
        // Now set it back again to closed to test idempotency
        circuitBreaker.failureCount = 0
        circuitBreaker.evaluateState()
        assertEquals(circuitBreaker.getState(), "CLOSED")
    }

    @Test
    fun testSetStateForBypass() {
        val circuitBreaker = DefaultCircuitBreaker(null, 1, 1, 2000L * 1000 * 1000)
        // Right now, failureCount<failureThreshold, so state should be closed
        // Bypass it and set it to open
        circuitBreaker.setState(State.OPEN)
        assertEquals(circuitBreaker.getState(), "OPEN")
    }

    @Test
    fun testApiResponses() {
        val mockService =
            object : RemoteService {
                override fun call(): String = "Remote Success"
            }
        val circuitBreaker = DefaultCircuitBreaker(mockService, 1, 1, 100)
        // Call with the parameter start_time set to huge amount of time in past so that service
        // replies with "Ok". Also, state is CLOSED in start
        val response = circuitBreaker.attemptRequest()
        assertEquals(response, "Remote Success")
    }
}