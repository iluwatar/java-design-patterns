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

// ABOUTME: Entry point demonstrating the leader-followers concurrency pattern.
// ABOUTME: Creates workers, submits tasks, and manages thread pool execution.
package com.iluwatar.leaderfollowers

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.abs

private val logger = KotlinLogging.logger {}

/**
 * Main entry point for the leader-followers pattern demonstration.
 *
 * @param args command line arguments (not used)
 * @throws InterruptedException if the execution is interrupted
 */
@Throws(InterruptedException::class)
fun main(args: Array<String>) {
    val taskSet = TaskSet()
    val taskHandler = TaskHandler()
    val workCenter = WorkCenter()
    workCenter.createWorkers(4, taskSet, taskHandler)
    execute(workCenter, taskSet)
}

@Throws(InterruptedException::class)
private fun execute(workCenter: WorkCenter, taskSet: TaskSet) {
    val workers = workCenter.workers
    val exec = Executors.newFixedThreadPool(workers.size)

    try {
        workers.forEach { exec.submit(it) }
        Thread.sleep(1000)
        addTasks(taskSet)
        val terminated = exec.awaitTermination(2, TimeUnit.SECONDS)
        if (!terminated) {
            logger.warn { "Executor did not terminate in the given time." }
        }
    } finally {
        exec.shutdownNow()
    }
}

@Throws(InterruptedException::class)
private fun addTasks(taskSet: TaskSet) {
    val rand = SecureRandom()
    for (i in 0 until 5) {
        val time = abs(rand.nextInt(1000))
        taskSet.addTask(Task(time))
    }
}
