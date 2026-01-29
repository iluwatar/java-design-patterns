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

// ABOUTME: Entry point for demonstrating the Fan-Out/Fan-In concurrency pattern.
// ABOUTME: Shows parallel processing of numbers with aggregated results.
package com.iluwatar.fanout.fanin

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * FanOut/FanIn pattern is a concurrency pattern that refers to executing multiple instances of the
 * activity function concurrently. The "fan out" part is essentially splitting the data into
 * multiple chunks and then calling the activity function multiple times, passing the chunks.
 *
 * When each chunk has been processed, the "fan in" takes place that aggregates results from each
 * instance of function and forms a single final result.
 *
 * This pattern is only really useful if you can "chunk" the workload in a meaningful way for
 * splitting up to be processed in parallel.
 */
fun main() {
    val numbers = listOf(1L, 3L, 4L, 7L, 8L)

    logger.info { "Numbers to be squared and get sum --> $numbers" }

    val requests = numbers.map { SquareNumberRequest(it) }

    val consumer = Consumer(0L)

    // Pass the request and the consumer to fanOutFanIn or sometimes referred as Orchestrator
    // function
    val sumOfSquaredNumbers = FanOutFanIn.fanOutFanIn(requests, consumer)

    logger.info { "Sum of all squared numbers --> $sumOfSquaredNumbers" }
}
