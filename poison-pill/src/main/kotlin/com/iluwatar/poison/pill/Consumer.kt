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

// ABOUTME: Consumer class responsible for receiving and handling messages from the queue.
// ABOUTME: Terminates processing when it receives the POISON_PILL message.

private val logger = KotlinLogging.logger {}

/** Class responsible for receiving and handling submitted to the queue messages. */
class Consumer(
    private val name: String,
    private val queue: MqSubscribePoint
) {

    /** Consume message. */
    fun consume() {
        while (true) {
            try {
                val msg = queue.take()
                if (Message.POISON_PILL == msg) {
                    logger.info { "Consumer $name receive request to terminate." }
                    break
                }
                val sender = msg.getHeader(Message.Headers.SENDER)
                val body = msg.getBody()
                logger.info { "Message [$body] from [$sender] received by [$name]" }
            } catch (e: InterruptedException) {
                // allow thread to exit
                logger.error(e) { "Exception caught." }
                return
            }
        }
    }
}
