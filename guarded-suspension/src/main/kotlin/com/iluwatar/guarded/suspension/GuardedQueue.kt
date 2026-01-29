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

// ABOUTME: Guarded Queue implementation using the Guarded Suspension concurrent pattern.
// ABOUTME: Threads block on get() until an element is available, using synchronized wait/notify.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.LinkedList
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.Condition

private val logger = KotlinLogging.logger {}

/**
 * Guarded Queue is an implementation for the Guarded Suspension pattern.
 * It is used to handle a situation when you want to execute a method on an
 * object which is not in a proper state.
 *
 * @see [Guarded Suspension](http://java-design-patterns.com/patterns/guarded-suspension/)
 */
class GuardedQueue {

    private val sourceList: LinkedList<Int> = LinkedList()
    private val lock = ReentrantLock()
    private val condition: Condition = lock.newCondition()

    /**
     * Get the last element of the queue if it exists.
     *
     * @return last element of a queue if queue is not empty
     */
    fun get(): Int {
        lock.lock()
        try {
            while (sourceList.isEmpty()) {
                try {
                    logger.info { "waiting" }
                    condition.await()
                } catch (e: InterruptedException) {
                    logger.error(e) { "Error occurred" }
                }
            }
            logger.info { "getting" }
            return sourceList.peek()
        } finally {
            lock.unlock()
        }
    }

    /**
     * Put a value in the queue.
     *
     * @param e number which we want to put to our queue
     */
    fun put(e: Int) {
        lock.lock()
        try {
            logger.info { "putting" }
            sourceList.add(e)
            logger.info { "notifying" }
            condition.signal()
        } finally {
            lock.unlock()
        }
    }
}
