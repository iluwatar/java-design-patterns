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
package com.iluwatar.event.asynchronous

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

// ABOUTME: Represents an event that runs as a separate thread.
// ABOUTME: Can be synchronous or asynchronous, with completion listener support.

private val logger = KotlinLogging.logger {}

/**
 * Each Event runs as a separate/individual thread.
 */
class AsyncEvent(
    private val eventId: Int,
    private val eventTime: Duration,
    val isSynchronous: Boolean
) : Event, Runnable {

    private var thread: Thread? = null
    private val isComplete = AtomicBoolean(false)
    private var eventListener: ThreadCompleteListener? = null

    override fun start() {
        thread = Thread(this).also { it.start() }
    }

    override fun stop() {
        thread?.interrupt()
    }

    override fun status() {
        if (isComplete.get()) {
            logger.info { "[$eventId] is not done." }
        } else {
            logger.info { "[$eventId] is done." }
        }
    }

    override fun run() {
        val currentTime = Instant.now()
        val endTime = currentTime.plusSeconds(eventTime.seconds)
        while (Instant.now() < endTime) {
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                logger.error(e) { "Thread was interrupted: " }
                Thread.currentThread().interrupt()
                return
            }
        }
        isComplete.set(true)
        completed()
    }

    fun addListener(listener: ThreadCompleteListener) {
        this.eventListener = listener
    }

    private fun completed() {
        eventListener?.completedEventHandler(eventId)
    }
}
