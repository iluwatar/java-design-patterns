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

// ABOUTME: Manages workers and leader election in the leader-followers pattern.
// ABOUTME: Responsible for creating workers, promoting leaders, and worker lifecycle.
package com.iluwatar.leaderfollowers

import java.util.concurrent.CopyOnWriteArrayList

/**
 * A WorkCenter contains a leader and a list of idle workers. The leader is responsible for
 * receiving work when it arrives. This class also provides a mechanism to promote a new leader. A
 * worker once he completes his task will add himself back to the center.
 */
class WorkCenter {

    var leader: Worker? = null
        private set

    val workers: MutableList<Worker> = CopyOnWriteArrayList()

    /**
     * Create workers and set leader.
     *
     * @param numberOfWorkers the number of workers to create
     * @param taskSet the task set for workers to use
     * @param taskHandler the task handler for processing tasks
     */
    fun createWorkers(numberOfWorkers: Int, taskSet: TaskSet, taskHandler: TaskHandler) {
        for (id in 1..numberOfWorkers) {
            val worker = Worker(id.toLong(), this, taskSet, taskHandler)
            workers.add(worker)
        }
        promoteLeader()
    }

    /**
     * Adds a worker back to the pool.
     *
     * @param worker the worker to add
     */
    fun addWorker(worker: Worker) {
        workers.add(worker)
    }

    /**
     * Removes a worker from the pool.
     *
     * @param worker the worker to remove
     */
    fun removeWorker(worker: Worker) {
        workers.remove(worker)
    }

    /**
     * Promote a leader from the available workers.
     * If no workers are available, the leader will be null.
     */
    fun promoteLeader() {
        leader = workers.firstOrNull()
    }
}
