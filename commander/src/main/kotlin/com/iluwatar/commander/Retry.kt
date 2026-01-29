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

// ABOUTME: Retry pattern implementation with exponential backoff.
// ABOUTME: Handles retrying operations and error handling with configurable parameters.
package com.iluwatar.commander

import java.security.SecureRandom
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min
import kotlin.math.pow

class Retry<T>(
    private val op: Operation,
    private val handleError: HandleErrorIssue<T>,
    private val maxAttempts: Int,
    private val maxDelay: Long,
    vararg ignoreTests: (Exception) -> Boolean,
) {
    fun interface Operation {
        @Throws(Exception::class)
        fun operation(list: MutableList<Exception>)
    }

    fun interface HandleErrorIssue<T> {
        fun handleIssue(
            obj: T?,
            e: Exception,
        )
    }

    private val attempts = AtomicInteger()
    private val test: (Exception) -> Boolean =
        ignoreTests
            .toList()
            .reduceOrNull { acc, predicate -> { e -> acc(e) || predicate(e) } }
            ?: { false }
    private val errors = mutableListOf<Exception>()

    fun perform(
        list: MutableList<Exception>,
        obj: T?,
    ) {
        do {
            try {
                op.operation(list)
                return
            } catch (e: Exception) {
                this.errors.add(e)
                if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test(e)) {
                    this.handleError.handleIssue(obj, e)
                    return
                }
                try {
                    val testDelay = 2.0.pow(this.attempts.get().toDouble()).toLong() * 1000 + RANDOM.nextInt(1000)
                    val delay = min(testDelay, this.maxDelay)
                    Thread.sleep(delay)
                } catch (f: InterruptedException) {
                    // ignore
                }
            }
        } while (true)
    }

    companion object {
        private val RANDOM = SecureRandom()
    }
}