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

// ABOUTME: Worker thread that participates in the leader-followers concurrency pattern.
// ABOUTME: Acts as leader to receive tasks or as follower waiting to be promoted.
package com.iluwatar.leaderfollowers

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Worker class that takes work from work center.
 *
 * @property id the unique identifier for this worker
 * @property workCenter the work center this worker belongs to
 * @property taskSet the task set to get tasks from
 * @property taskHandler the handler to process tasks
 */
class Worker(
    private val id: Long,
    private val workCenter: WorkCenter,
    private val taskSet: TaskSet,
    private val taskHandler: TaskHandler
) : Runnable {

    /**
     * The leader thread listens for task. When task arrives, it promotes one of the followers to be
     * the new leader. Then it handles the task and add himself back to work center.
     */
    override fun run() {
        while (!Thread.interrupted()) {
            try {
                var shouldContinue = false
                if (workCenter.leader != null && workCenter.leader != this) {
                    synchronized(workCenter) {
                        if (workCenter.leader != null && workCenter.leader != this) {
                            (workCenter as Object).wait()
                            shouldContinue = true
                        }
                    }
                }
                if (shouldContinue) {
                    continue
                }
                val task = taskSet.getTask()
                synchronized(workCenter) {
                    workCenter.removeWorker(this)
                    workCenter.promoteLeader()
                    (workCenter as Object).notifyAll()
                }
                taskHandler.handleTask(task)
                logger.info { "The Worker with the ID $id completed the task" }
                workCenter.addWorker(this)
            } catch (e: InterruptedException) {
                logger.warn { "Worker interrupted" }
                Thread.currentThread().interrupt()
                return
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Worker
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
