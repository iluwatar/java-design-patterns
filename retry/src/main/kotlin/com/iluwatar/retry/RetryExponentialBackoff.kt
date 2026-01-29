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

// ABOUTME: Decorator that adds retry capabilities with exponential backoff to a business operation.
// ABOUTME: Increases delay exponentially between retry attempts up to a maximum delay.
package com.iluwatar.retry

import java.util.Random
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min
import kotlin.math.pow

/**
 * Decorates [BusinessOperation] with "retry" capabilities using exponential backoff.
 *
 * @param T the remote op's return type
 * @param op the [BusinessOperation] to retry
 * @param maxAttempts number of times to retry
 * @param maxDelay maximum delay (in milliseconds) between attempts
 * @param ignoreTests tests to check whether the remote exception can be ignored. No exceptions
 *     will be ignored if no tests are given
 */
class RetryExponentialBackoff<T>(
    private val op: BusinessOperation<T>,
    private val maxAttempts: Int,
    private val maxDelay: Long,
    vararg ignoreTests: (Exception) -> Boolean
) : BusinessOperation<T> {

    private val attempts = AtomicInteger()
    private val test: (Exception) -> Boolean = ignoreTests
        .reduceOrNull { acc, predicate -> { e -> acc(e) || predicate(e) } }
        ?: { false }
    private val _errors = mutableListOf<Exception>()

    /**
     * The errors encountered while retrying, in the encounter order.
     *
     * @return the errors encountered while retrying
     */
    fun errors(): List<Exception> = _errors.toList()

    /**
     * The number of retries performed.
     *
     * @return the number of retries performed
     */
    fun attempts(): Int = attempts.get()

    @Throws(BusinessException::class)
    override fun perform(): T {
        while (true) {
            try {
                return op.perform()
            } catch (e: BusinessException) {
                _errors.add(e)

                if (attempts.incrementAndGet() >= maxAttempts || !test(e)) {
                    throw e
                }

                try {
                    val testDelay = 2.0.pow(attempts()).toLong() * 1000 + RANDOM.nextInt(1000)
                    val delay = min(testDelay, maxDelay)
                    Thread.sleep(delay)
                } catch (_: InterruptedException) {
                    // ignore
                }
            }
        }
    }

    companion object {
        private val RANDOM = Random()
    }
}
