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

// ABOUTME: Abstract base class for actors in the actor model pattern.
// ABOUTME: Provides mailbox-based message processing with thread-safe operation.
package com.iluwatar.actormodel

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

/**
 * Abstract base class for actors in the actor model.
 *
 * Each actor has its own mailbox (message queue) and processes messages one at a time.
 * Actors communicate only through messages - they do not share memory.
 */
abstract class Actor : Runnable {

    var actorId: String = ""

    private val mailbox: BlockingQueue<Message> = LinkedBlockingQueue()

    // Always read from main memory and written back to main memory,
    // rather than being cached in a thread's local memory. To make it consistent to all Actors
    @Volatile
    private var active = true

    /**
     * Sends a message to this actor's mailbox.
     *
     * @param message The message to send
     */
    fun send(message: Message) {
        mailbox.add(message)
    }

    /**
     * Stops the actor loop gracefully.
     */
    fun stop() {
        active = false
    }

    override fun run() {
        while (active) {
            try {
                val message = mailbox.take() // Wait for a message
                onReceive(message) // Process it
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    /**
     * Child classes must define what to do with a message.
     *
     * @param message The received message to process
     */
    internal abstract fun onReceive(message: Message)
}
