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

// ABOUTME: Orchestrator for the Fan-Out/Fan-In pattern using CompletableFuture.
// ABOUTME: Distributes work across threads and aggregates results via Consumer.
package com.iluwatar.fanout.fanin

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * FanOutFanIn class processes long-running requests, when any of the processes gets over, result is
 * passed over to the consumer or the callback function. Consumer will aggregate the results as they
 * keep on completing.
 */
object FanOutFanIn {
    /**
     * The main fanOutFanIn function or orchestrator function.
     *
     * @param requests List of numbers that need to be squared and summed up
     * @param consumer Takes in the squared number from [SquareNumberRequest] and sums it up
     * @return Aggregated sum of all squared numbers.
     */
    fun fanOutFanIn(
        requests: List<SquareNumberRequest>,
        consumer: Consumer,
    ): Long {
        val service = Executors.newFixedThreadPool(requests.size)

        // fanning out
        val futures =
            requests.map { request ->
                CompletableFuture.runAsync({ request.delayedSquaring(consumer) }, service)
            }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        return consumer.sumOfSquaredNumbers.get()
    }
}
