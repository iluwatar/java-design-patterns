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

// ABOUTME: Interface defining the circuit breaker contract for handling remote service failures.
// ABOUTME: Provides methods for recording success/failure, state management, and request attempts.

/**
 * The Circuit breaker interface.
 */
interface CircuitBreaker {
    /**
     * Success response. Reset everything to defaults.
     */
    fun recordSuccess()

    /**
     * Failure response. Handle accordingly with response and change state if required.
     */
    fun recordFailure(response: String)

    /**
     * Get the current state of circuit breaker.
     */
    fun getState(): String

    /**
     * Set the specific state manually.
     */
    fun setState(state: State)

    /**
     * Attempt to fetch response from the remote service.
     */
    @Throws(RemoteServiceException::class)
    fun attemptRequest(): String
}