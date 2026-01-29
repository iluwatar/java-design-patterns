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

// ABOUTME: Represents an asynchronous request to square a number with simulated delay.
// ABOUTME: The delay simulates a long-running process that returns at different times.
package com.iluwatar.fanout.fanin

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * Squares the number with a little timeout to give impression of long-running process that return
 * at different times.
 */
class SquareNumberRequest(private val number: Long) {
    /**
     * Squares the number with a little timeout to give impression of long-running process that
     * return at different times.
     *
     * @param consumer callback class that takes the result after the delay.
     */
    fun delayedSquaring(consumer: Consumer) {
        val minTimeOut = 5000L

        val secureRandom = SecureRandom()
        val randomTimeOut = secureRandom.nextInt(2000)

        try {
            // this will make the thread sleep from 5-7s.
            Thread.sleep(minTimeOut + randomTimeOut)
        } catch (e: InterruptedException) {
            logger.error(e) { "Exception while sleep" }
            Thread.currentThread().interrupt()
        } finally {
            consumer.add(number * number)
        }
    }
}
