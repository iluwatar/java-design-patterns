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

// ABOUTME: Comprehensive test suite for ThreadAsyncExecutor covering success, failure, and edge cases.
// ABOUTME: Tests async execution with and without callbacks, long-running tasks, and null handling.
package com.iluwatar.async.method.invocation

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.time.Duration.ofMillis
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException

/**
 * ThreadAsyncExecutorTest
 */
class ThreadAsyncExecutorTest {
    /**
     * Test used to verify the happy path of [ThreadAsyncExecutor.startProcess]
     */
    @Test
    fun testSuccessfulTaskWithoutCallback() {
        assertTimeout(ofMillis(3000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()

            val result = Any()
            val task = mockk<Callable<Any>>()
            every { task.call() } returns result

            val asyncResult = executor.startProcess(task)
            assertNotNull(asyncResult)
            asyncResult.await() // Prevent timing issues, and wait until the result is available
            assertTrue(asyncResult.isCompleted)

            // Our task should only execute once ...
            verify(exactly = 1) { task.call() }

            // ... and the result should be exactly the same object
            assertSame(result, asyncResult.getValue())
        }
    }

    /**
     * Test used to verify the happy path of [ThreadAsyncExecutor.startProcess]
     */
    @Test
    fun testSuccessfulTaskWithCallback() {
        assertTimeout(ofMillis(3000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()

            val result = Any()
            val task = mockk<Callable<Any>>()
            val callback = mockk<AsyncCallback<Any>>(relaxed = true)
            every { task.call() } returns result

            val asyncResult = executor.startProcess(task, callback)
            assertNotNull(asyncResult)
            asyncResult.await() // Prevent timing issues, and wait until the result is available
            assertTrue(asyncResult.isCompleted)

            // Our task should only execute once ...
            verify(exactly = 1) { task.call() }

            // ... same for the callback, we expect our object
            verify(exactly = 1) { callback.onComplete(result) }
            verify(exactly = 0) { callback.onError(any()) }

            // ... and the result should be exactly the same object
            assertSame(result, asyncResult.getValue())
        }
    }

    /**
     * Test used to verify the happy path of [ThreadAsyncExecutor.startProcess] when a
     * task takes a while to execute
     */
    @Test
    fun testLongRunningTaskWithoutCallback() {
        assertTimeout(ofMillis(5000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()

            val result = Any()
            val task = mockk<Callable<Any>>()
            every { task.call() } answers {
                Thread.sleep(1500)
                result
            }

            val asyncResult = executor.startProcess(task)
            assertNotNull(asyncResult)
            assertFalse(asyncResult.isCompleted)

            try {
                asyncResult.getValue()
                fail("Expected IllegalStateException when calling AsyncResult#getValue on a non-completed task")
            } catch (e: IllegalStateException) {
                assertNotNull(e.message)
            }

            // Prevent timing issues, and wait until the result is available
            asyncResult.await()
            assertTrue(asyncResult.isCompleted)

            // Our task should only execute once ...
            verify(exactly = 1) { task.call() }

            // ... and the result should be exactly the same object
            assertSame(result, asyncResult.getValue())
        }
    }

    /**
     * Test used to verify the happy path of [ThreadAsyncExecutor.startProcess] when a task takes a while to execute
     */
    @Test
    fun testLongRunningTaskWithCallback() {
        assertTimeout(ofMillis(5000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()

            val result = Any()
            val task = mockk<Callable<Any>>()
            val callback = mockk<AsyncCallback<Any>>(relaxed = true)
            every { task.call() } answers {
                Thread.sleep(1500)
                result
            }

            val asyncResult = executor.startProcess(task, callback)
            assertNotNull(asyncResult)
            assertFalse(asyncResult.isCompleted)

            try {
                asyncResult.getValue()
                fail("Expected IllegalStateException when calling AsyncResult#getValue on a non-completed task")
            } catch (e: IllegalStateException) {
                assertNotNull(e.message)
            }

            // Prevent timing issues, and wait until the result is available
            asyncResult.await()
            assertTrue(asyncResult.isCompleted)

            // Our task should only execute once ...
            verify(exactly = 1) { task.call() }
            verify(exactly = 1) { callback.onComplete(result) }
            verify(exactly = 0) { callback.onError(any()) }

            // ... and the result should be exactly the same object
            assertSame(result, asyncResult.getValue())
        }
    }

    /**
     * Test used to verify the happy path of [ThreadAsyncExecutor.startProcess] when a
     * task takes a while to execute, while waiting on the result using
     * [ThreadAsyncExecutor.endProcess]
     */
    @Test
    fun testEndProcess() {
        assertTimeout(ofMillis(5000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()

            val result = Any()
            val task = mockk<Callable<Any>>()
            every { task.call() } answers {
                Thread.sleep(1500)
                result
            }

            val asyncResult = executor.startProcess(task)
            assertNotNull(asyncResult)
            assertFalse(asyncResult.isCompleted)

            try {
                asyncResult.getValue()
                fail("Expected IllegalStateException when calling AsyncResult#getValue on a non-completed task")
            } catch (e: IllegalStateException) {
                assertNotNull(e.message)
            }

            assertSame(result, executor.endProcess(asyncResult))
            verify(exactly = 1) { task.call() }
            assertTrue(asyncResult.isCompleted)

            // Calling end process a second time while already finished should give the same result
            assertSame(result, executor.endProcess(asyncResult))
        }
    }

    /**
     * Test used to verify the behaviour of [ThreadAsyncExecutor.startProcess] when
     * the callable is 'null'
     */
    @Test
    fun testNullTask() {
        assertTimeout(ofMillis(3000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()
            val asyncResult = executor.startProcess<Any>(null)

            assertNotNull(asyncResult, "The AsyncResult should not be 'null', even though the task was 'null'.")
            asyncResult.await() // Prevent timing issues, and wait until the result is available
            assertTrue(asyncResult.isCompleted)

            try {
                asyncResult.getValue()
                fail("Expected ExecutionException with NPE as cause")
            } catch (e: ExecutionException) {
                assertNotNull(e.message)
                assertNotNull(e.cause)
                assertEquals(NullPointerException::class.java, e.cause!!.javaClass)
            }
        }
    }

    /**
     * Test used to verify the behaviour of [ThreadAsyncExecutor.startProcess] when the callable is 'null', but the asynchronous callback is provided
     */
    @Test
    fun testNullTaskWithCallback() {
        assertTimeout(ofMillis(3000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()
            val callback = mockk<AsyncCallback<Any>>(relaxed = true)
            val exceptionSlot = slot<Exception>()
            every { callback.onError(capture(exceptionSlot)) } returns Unit

            val asyncResult = executor.startProcess(null, callback)

            assertNotNull(asyncResult, "The AsyncResult should not be 'null', even though the task was 'null'.")
            asyncResult.await() // Prevent timing issues, and wait until the result is available
            assertTrue(asyncResult.isCompleted)
            verify(exactly = 0) { callback.onComplete(any()) }
            verify(exactly = 1) { callback.onError(any()) }

            val exception = exceptionSlot.captured
            assertNotNull(exception)
            assertEquals(NullPointerException::class.java, exception.javaClass)

            try {
                asyncResult.getValue()
                fail("Expected ExecutionException with NPE as cause")
            } catch (e: ExecutionException) {
                assertNotNull(e.message)
                assertNotNull(e.cause)
                assertEquals(NullPointerException::class.java, e.cause!!.javaClass)
            }
        }
    }

    /**
     * Test used to verify the behaviour of [ThreadAsyncExecutor.startProcess] when both the callable and the asynchronous callback are 'null'
     */
    @Test
    fun testNullTaskWithNullCallback() {
        assertTimeout(ofMillis(3000)) {
            // Instantiate a new executor and start a new 'null' task ...
            val executor = ThreadAsyncExecutor()
            val asyncResult = executor.startProcess<Any>(null, null)

            assertNotNull(asyncResult, "The AsyncResult should not be 'null', even though the task and callback were 'null'.")
            asyncResult.await() // Prevent timing issues, and wait until the result is available
            assertTrue(asyncResult.isCompleted)

            try {
                asyncResult.getValue()
                fail("Expected ExecutionException with NPE as cause")
            } catch (e: ExecutionException) {
                assertNotNull(e.message)
                assertNotNull(e.cause)
                assertEquals(NullPointerException::class.java, e.cause!!.javaClass)
            }
        }
    }
}