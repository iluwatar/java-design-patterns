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
package com.iluwatar.backpressure

// ABOUTME: Custom subscriber that implements backpressure by controlling request rate.
// ABOUTME: Requests items in batches of 5 after initial request of 10 to handle slow processing.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber

private val logger = KotlinLogging.logger {}

/**
 * This class is the custom subscriber that subscribes to the data stream.
 */
class Subscriber : BaseSubscriber<Int>() {

    override fun hookOnSubscribe(subscription: Subscription) {
        request(10) // request 10 items initially
    }

    override fun hookOnNext(value: Int) {
        processItem()
        logger.info { "process($value)" }
        if (value % 5 == 0) {
            // request for the next 5 items after processing first 5
            request(5)
        }
    }

    override fun hookOnComplete() {
        App.latch?.countDown()
    }

    private fun processItem() {
        try {
            Thread.sleep(500) // simulate slow processing
        } catch (e: InterruptedException) {
            logger.error(e) { e.message }
            Thread.currentThread().interrupt()
        }
    }
}
