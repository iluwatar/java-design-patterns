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

// ABOUTME: Default implementation of the circuit breaker pattern with CLOSED->OPEN->HALF_OPEN flow.
// ABOUTME: Tracks failures and manages state transitions based on failure threshold and retry period.

/**
 * The delay based Circuit breaker implementation that works in a
 * CLOSED->OPEN-(retry_time_period)->HALF_OPEN->CLOSED flow with some retry time period for failed
 * services and a failure threshold for service to open circuit.
 *
 * @param service the remote service to call
 * @param timeout Timeout for the API request. Not necessary for this simple example
 * @param failureThreshold Number of failures we receive from the depended on service before
 *     changing state to 'OPEN'
 * @param retryTimePeriod Time, in nanoseconds, period after which a new request is made to remote
 *     service for status check.
 */
internal class DefaultCircuitBreaker(
    private val service: RemoteService?,
    private val timeout: Long,
    private val failureThreshold: Int,
    private val retryTimePeriod: Long,
) : CircuitBreaker {
    // Future time offset, in nanoseconds
    private val futureTime = 1_000_000_000_000L

    // We start in a closed state hoping that everything is fine
    private var state: State = State.CLOSED

    // An absurd amount of time in future which basically indicates the last failure never happened
    var lastFailureTime: Long = System.nanoTime() + futureTime

    private var lastFailureResponse: String? = null

    var failureCount: Int = 0

    /**
     * Reset everything to defaults.
     */
    override fun recordSuccess() {
        failureCount = 0
        lastFailureTime = System.nanoTime() + futureTime
        state = State.CLOSED
    }

    override fun recordFailure(response: String) {
        failureCount += 1
        lastFailureTime = System.nanoTime()
        // Cache the failure response for returning on open state
        lastFailureResponse = response
    }

    /**
     * Evaluate the current state based on failureThreshold, failureCount and lastFailureTime.
     */
    internal fun evaluateState() {
        if (failureCount >= failureThreshold) {
            // Then something is wrong with remote service
            if ((System.nanoTime() - lastFailureTime) > retryTimePeriod) {
                // We have waited long enough and should try checking if service is up
                state = State.HALF_OPEN
            } else {
                // Service would still probably be down
                state = State.OPEN
            }
        } else {
            // Everything is working fine
            state = State.CLOSED
        }
    }

    override fun getState(): String {
        evaluateState()
        return state.name
    }

    /**
     * Break the circuit beforehand if it is known service is down Or connect the circuit manually if
     * service comes online before expected.
     *
     * @param state State at which circuit is in
     */
    override fun setState(state: State) {
        this.state = state
        when (state) {
            State.OPEN -> {
                failureCount = failureThreshold
                lastFailureTime = System.nanoTime()
            }
            State.HALF_OPEN -> {
                failureCount = failureThreshold
                lastFailureTime = System.nanoTime() - retryTimePeriod
            }
            else -> failureCount = 0
        }
    }

    /**
     * Executes service call.
     *
     * @return Value from the remote resource, stale response or a custom exception
     */
    override fun attemptRequest(): String {
        evaluateState()
        if (state == State.OPEN) {
            // return cached response if the circuit is in OPEN state
            return lastFailureResponse!!
        } else {
            // Make the API request if the circuit is not OPEN
            try {
                // In a real application, this would be run in a thread and the timeout
                // parameter of the circuit breaker would be utilized to know if service
                // is working. Here, we simulate that based on server response itself
                val response = service!!.call()
                // Yay!! the API responded fine. Let's reset everything.
                recordSuccess()
                return response
            } catch (ex: RemoteServiceException) {
                recordFailure(ex.message!!)
                throw ex
            }
        }
    }
}