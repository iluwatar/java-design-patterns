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

// ABOUTME: Tests for WorkCenter worker management and leader promotion.
// ABOUTME: Verifies worker creation, removal, and leader election logic.
package com.iluwatar.leaderfollowers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * Tests for WorkCenter
 */
class WorkCenterTest {

    @Test
    fun testCreateWorkers() {
        val taskSet = TaskSet()
        val taskHandler = TaskHandler()
        val workCenter = WorkCenter()
        workCenter.createWorkers(5, taskSet, taskHandler)
        assertEquals(5, workCenter.workers.size)
        assertEquals(workCenter.workers[0], workCenter.leader)
    }

    @Test
    fun testNullLeader() {
        val workCenter = WorkCenter()
        workCenter.promoteLeader()
        assertNull(workCenter.leader)
    }

    @Test
    fun testPromoteLeader() {
        val taskSet = TaskSet()
        val taskHandler = TaskHandler()
        val workCenter = WorkCenter()
        workCenter.createWorkers(5, taskSet, taskHandler)
        workCenter.removeWorker(workCenter.leader!!)
        workCenter.promoteLeader()
        assertEquals(4, workCenter.workers.size)
        assertEquals(workCenter.workers[0], workCenter.leader)
    }
}
