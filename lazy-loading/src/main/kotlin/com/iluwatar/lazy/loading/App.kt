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

package com.iluwatar.lazy.loading

// ABOUTME: Demonstrates the lazy loading idiom with different implementations.
// ABOUTME: Shows naive, thread-safe, and efficient Java 8 supplier-based approaches.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Lazy loading idiom defers object creation until needed.
 *
 * This example shows different implementations of the pattern with increasing sophistication.
 *
 * Additional information and lazy loading flavours are described in
 * http://martinfowler.com/eaaCatalog/lazyLoad.html
 */
fun main() {
    // Simple lazy loader - not thread safe
    val holderNaive = HolderNaive()
    val heavy = holderNaive.getHeavy()
    logger.info { "heavy=$heavy" }

    // Thread safe lazy loader, but with heavy synchronization on each access
    val holderThreadSafe = HolderThreadSafe()
    val another = holderThreadSafe.getHeavy()
    logger.info { "another=$another" }

    // The most efficient lazy loader utilizing Java 8 features
    val java8Holder = Java8Holder()
    val next = java8Holder.getHeavy()
    logger.info { "next=$next" }
}
