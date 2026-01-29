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

// ABOUTME: Decorator that adds retry capabilities to a business operation with fixed delay.
// ABOUTME: Retries failed operations a configurable number of times with a constant delay between attempts.
package com.iluwatar.retry

import java.util.concurrent.atomic.AtomicInteger

/**
 * Decorates [BusinessOperation] with "retry" capabilities.
 *
 * @param T the remote op's return type
 * @param op the [BusinessOperation] to retry
 * @param maxAttempts number of times to retry
 * @param delay delay (in milliseconds) between attempts
 * @param ignoreTests tests to check whether the remote exception can be ignored. No exceptions
 *     will be ignored if no tests are given
 */
class Retry<T>(
    private val op: BusinessOperation<T>,
    private val maxAttempts: Int,
    private val delay: Long,
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
                    Thread.sleep(delay)
                } catch (_: InterruptedException) {
                    // ignore
                }
            }
        }
    }
}
