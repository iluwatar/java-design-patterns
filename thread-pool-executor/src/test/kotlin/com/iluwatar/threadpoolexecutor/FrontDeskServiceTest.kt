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

// ABOUTME: Test class for FrontDeskService thread pool management.
// ABOUTME: Verifies thread pool initialization, task submission, and shutdown behavior.
package com.iluwatar.threadpoolexecutor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class FrontDeskServiceTest {

    /**
     * Tests that the constructor correctly sets the number of employees (threads). This verifies the
     * basic initialization of the thread pool.
     */
    @Test
    fun testConstructorSetsCorrectNumberOfEmployees() {
        val expectedEmployees = 3

        val frontDesk = FrontDeskService(expectedEmployees)

        assertEquals(expectedEmployees, frontDesk.numberOfEmployees)
    }

    /**
     * Tests that the submitGuestCheckIn method returns a non-null Future object. This verifies the
     * basic task submission functionality.
     */
    @Test
    fun testSubmitGuestCheckInReturnsNonNullFuture() {
        val frontDesk = FrontDeskService(1)

        val task = Runnable {
            // Task that completes quickly
        }

        val future = frontDesk.submitGuestCheckIn(task)

        assertNotNull(future)
    }

    /**
     * Tests that the submitVipGuestCheckIn method returns a non-null Future object. This verifies
     * that tasks with return values can be submitted correctly.
     */
    @Test
    fun testSubmitVipGuestCheckInReturnsNonNullFuture() {
        val frontDesk = FrontDeskService(1)
        val task = Callable { "VIP Check-in complete" }

        val future = frontDesk.submitVipGuestCheckIn(task)

        assertNotNull(future)
    }

    /**
     * Tests that the shutdown and awaitTermination methods work correctly. This verifies the basic
     * shutdown functionality of the thread pool.
     */
    @Test
    fun testShutdownAndAwaitTermination() {
        val frontDesk = FrontDeskService(2)
        val taskLatch = CountDownLatch(1)

        val task = Runnable { taskLatch.countDown() }

        frontDesk.submitGuestCheckIn(task)
        frontDesk.shutdown()
        val terminated = frontDesk.awaitTermination(1, TimeUnit.SECONDS)

        assertTrue(terminated)
        assertTrue(taskLatch.await(100, TimeUnit.MILLISECONDS))
    }

    /**
     * Tests the thread pool's behavior under load with multiple tasks. This verifies that the thread
     * pool limits concurrent execution to the number of threads, all submitted tasks are eventually
     * completed, and threads are reused for multiple tasks.
     */
    @Test
    fun testMultipleTasksUnderLoad() {
        val frontDesk = FrontDeskService(2)
        val taskCount = 10
        val tasksCompletedLatch = CountDownLatch(taskCount)
        val concurrentTasks = AtomicInteger(0)
        val maxConcurrentTasks = AtomicInteger(0)

        for (i in 0 until taskCount) {
            frontDesk.submitGuestCheckIn {
                try {
                    val current = concurrentTasks.incrementAndGet()
                    maxConcurrentTasks.updateAndGet { max -> maxOf(max, current) }

                    Thread.sleep(100)

                    concurrentTasks.decrementAndGet()
                    tasksCompletedLatch.countDown()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }

        val allTasksCompleted = tasksCompletedLatch.await(2, TimeUnit.SECONDS)

        frontDesk.shutdown()
        frontDesk.awaitTermination(1, TimeUnit.SECONDS)

        assertTrue(allTasksCompleted)
        assertEquals(2, maxConcurrentTasks.get())
        assertEquals(0, concurrentTasks.get())
    }

    /**
     * Tests proper shutdown behavior under load. This verifies that after shutdown no new tasks are
     * accepted, all previously submitted tasks are completed, and the executor terminates properly
     * after all tasks complete.
     */
    @Test
    fun testProperShutdownUnderLoad() {
        val frontDesk = FrontDeskService(2)
        val taskCount = 5
        val startedTasksLatch = CountDownLatch(2)
        val tasksCompletionLatch = CountDownLatch(taskCount)

        for (i in 0 until taskCount) {
            frontDesk.submitGuestCheckIn {
                try {
                    startedTasksLatch.countDown()
                    Thread.sleep(100)
                    tasksCompletionLatch.countDown()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }

        assertTrue(startedTasksLatch.await(1, TimeUnit.SECONDS))

        frontDesk.shutdown()

        assertThrows(RejectedExecutionException::class.java) {
            frontDesk.submitGuestCheckIn {}
        }

        val allTasksCompleted = tasksCompletionLatch.await(2, TimeUnit.SECONDS)

        val terminated = frontDesk.awaitTermination(1, TimeUnit.SECONDS)

        assertTrue(allTasksCompleted)
        assertTrue(terminated)
    }

    /**
     * Tests concurrent execution of different task types (regular and VIP). This verifies that both
     * Runnable and Callable tasks can be processed concurrently, all tasks complete successfully, and
     * Callable tasks return their results correctly.
     */
    @Test
    fun testConcurrentRegularAndVipTasks() {
        val frontDesk = FrontDeskService(3)
        val regularTaskCount = 4
        val vipTaskCount = 3
        val allTasksLatch = CountDownLatch(regularTaskCount + vipTaskCount)

        val regularResults = mutableListOf<java.util.concurrent.Future<*>>()
        for (i in 0 until regularTaskCount) {
            val result = frontDesk.submitGuestCheckIn {
                try {
                    Thread.sleep(50)
                    allTasksLatch.countDown()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
            regularResults.add(result)
        }

        val vipResults = mutableListOf<java.util.concurrent.Future<String>>()
        for (i in 0 until vipTaskCount) {
            val guestNum = i
            val result = frontDesk.submitVipGuestCheckIn(Callable {
                Thread.sleep(25)
                allTasksLatch.countDown()
                "VIP-$guestNum checked in"
            })
            vipResults.add(result)
        }

        val allCompleted = allTasksLatch.await(2, TimeUnit.SECONDS)

        frontDesk.shutdown()
        frontDesk.awaitTermination(1, TimeUnit.SECONDS)

        assertTrue(allCompleted)

        for (result in regularResults) {
            assertTrue(result.isDone)
        }

        for (i in 0 until vipTaskCount) {
            val result = vipResults[i]
            assertTrue(result.isDone)
            assertEquals("VIP-$i checked in", result.get())
        }
    }
}
