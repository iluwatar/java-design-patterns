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
package com.iluwatar.trampoline

// ABOUTME: Entry point demonstrating the Trampoline pattern with a factorial computation.
// ABOUTME: Shows how recursive algorithms can be made stack-safe using iterative trampolining.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Trampoline pattern allows defining recursive algorithms by iterative loop.
 *
 * It is possible to implement algorithms recursively without blowing the stack and to
 * interleave the execution of functions without hard coding them together or even using threads.
 */
fun main() {
    logger.info { "Start calculating war casualties" }
    val result = loop(10, 1).result()
    logger.info { "The number of orcs perished in the war: $result" }
}

/** Factorial computation using the Trampoline pattern. */
fun loop(times: Int, prod: Int): Trampoline<Int> =
    if (times == 0) {
        done(prod)
    } else {
        more { loop(times - 1, prod * times) }
    }
