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

// ABOUTME: Thread-safe collection of tasks using a blocking queue.
// ABOUTME: The leader receives tasks from here for distribution to workers.
package com.iluwatar.leaderfollowers

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

/**
 * A TaskSet is a collection of the tasks, the leader receives task from here.
 */
class TaskSet {

    private val queue: BlockingQueue<Task> = ArrayBlockingQueue(100)

    /**
     * Adds a task to the queue. Blocks if the queue is full.
     *
     * @param task the task to add
     * @throws InterruptedException if interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun addTask(task: Task) {
        queue.put(task)
    }

    /**
     * Retrieves and removes a task from the queue. Blocks if the queue is empty.
     *
     * @return the task from the queue
     * @throws InterruptedException if interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun getTask(): Task {
        return queue.take()
    }

    /**
     * Returns the current number of tasks in the queue.
     *
     * @return the size of the queue
     */
    val size: Int
        get() = queue.size
}
