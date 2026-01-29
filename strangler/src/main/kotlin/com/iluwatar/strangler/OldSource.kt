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

package com.iluwatar.strangler

// ABOUTME: Old source implementation using outdated techniques (imperative loops).
// ABOUTME: Provides accumulate sum and multiply operations for the legacy system.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/** Old source with techniques out of date. */
class OldSource {

    /** Implement accumulate sum with old technique. */
    fun accumulateSum(vararg nums: Int): Int {
        logger.info { "Source module $VERSION" }
        var sum = 0
        for (num in nums) {
            sum += num
        }
        return sum
    }

    /** Implement accumulate multiply with old technique. */
    fun accumulateMul(vararg nums: Int): Int {
        logger.info { "Source module $VERSION" }
        var product = 1
        for (num in nums) {
            product *= num
        }
        return product
    }

    companion object {
        private const val VERSION = "1.0"
    }
}
