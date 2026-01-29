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
package com.iluwatar.event.queue

import io.github.oshai.kotlinlogging.KotlinLogging

// ABOUTME: Entry point for the Event Queue pattern demonstration.
// ABOUTME: Demonstrates asynchronous audio playback using an event queue.

private val logger = KotlinLogging.logger {}

/**
 * Event or message queues provide an asynchronous communications protocol, meaning that the sender
 * and receiver of the message do not need to interact with the message queue at the same time.
 * Events or messages placed onto the queue are stored until the recipient retrieves them. Event or
 * message queues have implicit or explicit limits on the size of data that may be transmitted in a
 * single message and the number of messages that may remain outstanding on the queue. A queue
 * stores a series of notifications or requests in first-in, first-out order. Sending a notification
 * enqueues the request and returns. The request processor then processes items from the queue at a
 * later time.
 */
fun main() {
    val audio = Audio.instance
    audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.wav"), -10.0f)
    audio.playSound(audio.getAudioStream("./etc/Closed-Hi-Hat-1.wav"), -8.0f)

    logger.info { "Press Enter key to stop the program..." }
    System.`in`.bufferedReader().use { it.read() }
    audio.stopService()
}
