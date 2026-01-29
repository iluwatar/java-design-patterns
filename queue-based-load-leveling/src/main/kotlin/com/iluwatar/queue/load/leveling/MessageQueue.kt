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

// ABOUTME: MessageQueue provides a thread-safe blocking queue for message handling.
// ABOUTME: Uses ArrayBlockingQueue to buffer messages between producers and consumers.
package com.iluwatar.queue.load.leveling

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

private val logger = KotlinLogging.logger {}

/**
 * MessageQueue class. In this class we will create a Blocking Queue and submit/retrieve all the
 * messages from it.
 */
class MessageQueue {

    private val blkQueue: BlockingQueue<Message> = ArrayBlockingQueue(1024)

    /**
     * All the TaskGenerator threads will call this method to insert the Messages in to the Blocking
     * Queue.
     */
    fun submitMsg(msg: Message?) {
        try {
            if (msg != null) {
                blkQueue.add(msg)
            }
        } catch (e: Exception) {
            logger.error { e.message }
        }
    }

    /**
     * All the messages will be retrieved by the ServiceExecutor by calling this method and process
     * them. Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    fun retrieveMsg(): Message? {
        return try {
            blkQueue.poll()
        } catch (e: Exception) {
            logger.error { e.message }
            null
        }
    }
}
