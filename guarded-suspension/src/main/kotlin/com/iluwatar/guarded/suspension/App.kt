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
package com.iluwatar.guarded.suspension

// ABOUTME: Entry point demonstrating the Guarded Suspension concurrent design pattern.
// ABOUTME: Shows how a consumer thread waits for a producer thread to add an element to a GuardedQueue.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * Guarded-suspension is a concurrent design pattern for handling situation when to execute some
 * action we need condition to be satisfied. The implementation utilizes a [GuardedQueue], which
 * features two primary methods: [GuardedQueue.get] and [GuardedQueue.put]. The key condition
 * governing these operations is that elements cannot be retrieved ([GuardedQueue.get]) from an
 * empty queue. When a thread attempts to retrieve an element under this condition, it triggers
 * a wait, causing the thread to pause. Conversely, when an element is added ([GuardedQueue.put])
 * to the queue by another thread, it signals the waiting thread that it can now successfully
 * retrieve an element from the queue.
 */
fun main() {
    val guardedQueue = GuardedQueue()
    val executorService = Executors.newFixedThreadPool(3)

    // here we create first thread which is supposed to get from guardedQueue
    executorService.execute { guardedQueue.get() }

    // here we wait two seconds to show that the thread which is trying
    // to get from guardedQueue will be waiting
    try {
        Thread.sleep(2000)
    } catch (e: InterruptedException) {
        logger.error(e) { "Error occurred" }
    }
    // now we execute second thread which will put number to guardedQueue
    // and notify first thread that it could get
    executorService.execute { guardedQueue.put(20) }
    executorService.shutdown()
    try {
        executorService.awaitTermination(30, TimeUnit.SECONDS)
    } catch (e: InterruptedException) {
        logger.error(e) { "Error occurred" }
    }
}
