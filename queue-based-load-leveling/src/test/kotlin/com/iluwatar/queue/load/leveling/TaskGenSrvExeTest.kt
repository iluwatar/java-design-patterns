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

// ABOUTME: Integration test for TaskGenerator and ServiceExecutor interaction.
// ABOUTME: Tests message submission by TaskGenerator and retrieval by ServiceExecutor.
package com.iluwatar.queue.load.leveling

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * Test case for submitting Message to Blocking Queue by TaskGenerator and retrieve the message by
 * ServiceExecutor.
 */
class TaskGenSrvExeTest {

    @Test
    fun taskGeneratorTest() {
        val msgQueue = MessageQueue()

        // Create a task generator thread with 1 job to submit.
        val taskRunnable = TaskGenerator(msgQueue, 1)
        val taskGenThr = Thread(taskRunnable)
        taskGenThr.start()

        assertNotNull(taskGenThr)

        // Create a service executor thread.
        val srvRunnable = ServiceExecutor(msgQueue)
        val srvExeThr = Thread(srvRunnable)
        srvExeThr.start()

        assertNotNull(srvExeThr)
    }
}
