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

// ABOUTME: Tests for the Promise class covering async fulfillment, chaining, and error handling.
// ABOUTME: Validates thenAccept, thenApply, onError, and exception propagation behaviors.
package com.iluwatar.promise

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/** Tests Promise class. */
class PromiseTest {

    private lateinit var executor: Executor
    private lateinit var promise: Promise<Int>

    @BeforeEach
    fun setUp() {
        executor = Executors.newSingleThreadExecutor()
        promise = Promise()
    }

    @Test
    fun promiseIsFulfilledWithTheResultantValueOfExecutingTheTask() {
        promise.fulfillInAsync(NumberCrunchingTask(), executor)

        assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, promise.get())
        assertTrue(promise.isDone)
        assertFalse(promise.isCancelled)
    }

    @Test
    fun promiseIsFulfilledWithAnExceptionIfTaskThrowsAnException() {
        testWaitingForeverForPromiseToBeFulfilled()
        testWaitingSomeTimeForPromiseToBeFulfilled()
    }

    private fun testWaitingForeverForPromiseToBeFulfilled() {
        val promise = Promise<Int>()
        promise.fulfillInAsync(
            Callable { throw RuntimeException("Barf!") },
            executor,
        )

        try {
            promise.get()
            fail("Fetching promise should result in exception if the task threw an exception")
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }

        try {
            promise.get(1000, TimeUnit.SECONDS)
            fail("Fetching promise should result in exception if the task threw an exception")
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }
    }

    private fun testWaitingSomeTimeForPromiseToBeFulfilled() {
        val promise = Promise<Int>()
        promise.fulfillInAsync(
            Callable { throw RuntimeException("Barf!") },
            executor,
        )

        try {
            promise.get(1000, TimeUnit.SECONDS)
            fail("Fetching promise should result in exception if the task threw an exception")
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }

        try {
            promise.get()
            fail("Fetching promise should result in exception if the task threw an exception")
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }
    }

    @Test
    fun dependentPromiseIsFulfilledAfterTheConsumerConsumesTheResultOfThisPromise() {
        val dependentPromise =
            promise
                .fulfillInAsync(NumberCrunchingTask(), executor)
                .thenAccept { value -> assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value) }

        dependentPromise.get()
        assertTrue(dependentPromise.isDone)
        assertFalse(dependentPromise.isCancelled)
    }

    @Test
    fun dependentPromiseIsFulfilledWithAnExceptionIfConsumerThrowsAnException() {
        val dependentPromise =
            promise
                .fulfillInAsync(NumberCrunchingTask(), executor)
                .thenAccept { throw RuntimeException("Barf!") }

        try {
            dependentPromise.get()
            fail(
                "Fetching dependent promise should result in exception " +
                    "if the action threw an exception",
            )
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }

        try {
            dependentPromise.get(1000, TimeUnit.SECONDS)
            fail(
                "Fetching dependent promise should result in exception " +
                    "if the action threw an exception",
            )
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }
    }

    @Test
    fun dependentPromiseIsFulfilledAfterTheFunctionTransformsTheResultOfThisPromise() {
        val dependentPromise =
            promise
                .fulfillInAsync(NumberCrunchingTask(), executor)
                .thenApply { value ->
                    assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value)
                    value.toString()
                }

        assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER.toString(), dependentPromise.get())
        assertTrue(dependentPromise.isDone)
        assertFalse(dependentPromise.isCancelled)
    }

    @Test
    fun dependentPromiseIsFulfilledWithAnExceptionIfTheFunctionThrowsException() {
        val dependentPromise =
            promise
                .fulfillInAsync(NumberCrunchingTask(), executor)
                .thenApply<String> { throw RuntimeException("Barf!") }

        try {
            dependentPromise.get()
            fail(
                "Fetching dependent promise should result in exception " +
                    "if the function threw an exception",
            )
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }

        try {
            dependentPromise.get(1000, TimeUnit.SECONDS)
            fail(
                "Fetching dependent promise should result in exception " +
                    "if the function threw an exception",
            )
        } catch (ex: ExecutionException) {
            assertTrue(promise.isDone)
            assertFalse(promise.isCancelled)
        }
    }

    @Test
    fun fetchingAnAlreadyFulfilledPromiseReturnsTheFulfilledValueImmediately() {
        val promise = Promise<Int>()
        promise.fulfill(NumberCrunchingTask.CRUNCHED_NUMBER)

        val result = promise.get(1000, TimeUnit.SECONDS)
        assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, result)
    }

    @Test
    fun exceptionHandlerIsCalledWhenPromiseIsFulfilledExceptionally() {
        val promise = Promise<Any>()
        val exceptionHandler = mockk<(Throwable) -> Unit>(relaxed = true)
        promise.onError(exceptionHandler)

        val exception = Exception("barf!")
        promise.fulfillExceptionally(exception)

        verify { exceptionHandler.invoke(exception) }
    }

    private class NumberCrunchingTask : Callable<Int> {
        override fun call(): Int {
            // Do number crunching
            Thread.sleep(100)
            return CRUNCHED_NUMBER
        }

        companion object {
            val CRUNCHED_NUMBER: Int = Int.MAX_VALUE
        }
    }
}
