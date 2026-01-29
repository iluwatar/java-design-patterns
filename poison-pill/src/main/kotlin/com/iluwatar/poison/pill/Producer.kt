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
package com.iluwatar.poison.pill

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Date

// ABOUTME: Producer class responsible for creating and sending messages to the queue.
// ABOUTME: Sends POISON_PILL to signal termination when stopped.

private val logger = KotlinLogging.logger {}

/**
 * Class responsible for producing unit of work that can be expressed as message and submitted to
 * queue.
 */
class Producer(
    private val name: String,
    private val queue: MqPublishPoint
) {
    private var isStopped = false

    /** Send message to queue. */
    fun send(body: String) {
        if (isStopped) {
            throw IllegalStateException(
                "Producer $body was stopped and fail to deliver requested message [$name]."
            )
        }
        val msg = SimpleMessage()
        msg.addHeader(Message.Headers.DATE, Date().toString())
        msg.addHeader(Message.Headers.SENDER, name)
        msg.setBody(body)

        try {
            queue.put(msg)
        } catch (e: InterruptedException) {
            // allow thread to exit
            logger.error(e) { "Exception caught." }
        }
    }

    /** Stop system by sending poison pill. */
    fun stop() {
        isStopped = true
        try {
            queue.put(Message.POISON_PILL)
        } catch (e: InterruptedException) {
            // allow thread to exit
            logger.error(e) { "Exception caught." }
        }
    }
}
