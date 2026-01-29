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

// ABOUTME: Implementation of AsyncExecutor that creates a new thread for every async task.
// ABOUTME: Contains CompletableResult inner class that manages task state and synchronization.
package com.iluwatar.async.method.invocation

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.atomic.AtomicInteger

/**
 * Implementation of async executor that creates a new thread for every task.
 */
class ThreadAsyncExecutor : AsyncExecutor {
    /** Index for thread naming. */
    private val idx = AtomicInteger(0)

    override fun <T> startProcess(task: Callable<T>?): AsyncResult<T> = startProcess(task, null)

    override fun <T> startProcess(
        task: Callable<T>?,
        callback: AsyncCallback<T>?,
    ): AsyncResult<T> {
        val result = CompletableResult(callback)
        Thread(
            {
                try {
                    result.setValue(task!!.call())
                } catch (ex: Exception) {
                    result.setException(ex)
                }
            },
            "executor-${idx.incrementAndGet()}",
        ).start()
        return result
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    override fun <T> endProcess(asyncResult: AsyncResult<T>): T {
        if (!asyncResult.isCompleted) {
            asyncResult.await()
        }
        return asyncResult.getValue()
    }

    /**
     * Simple implementation of async result that allows completing it successfully with a value or
     * exceptionally with an exception. A really simplified version from its real life cousins
     * FutureTask and CompletableFuture.
     *
     * @see java.util.concurrent.FutureTask
     * @see java.util.concurrent.CompletableFuture
     */
    internal class CompletableResult<T>(
        private val callback: AsyncCallback<T>?,
    ) : AsyncResult<T> {
        private val lock = Any()

        @Volatile
        private var state = RUNNING

        private var value: T? = null
        private var exception: Exception? = null

        override val isCompleted: Boolean
            get() = state > RUNNING

        private fun hasCallback(): Boolean = callback != null

        /**
         * Sets the value from successful execution and executes callback if available. Notifies any
         * thread waiting for completion.
         *
         * @param value value of the evaluated task
         */
        internal fun setValue(value: T) {
            this.value = value
            this.state = COMPLETED
            if (hasCallback()) {
                callback!!.onComplete(value)
            }
            synchronized(lock) {
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                (lock as java.lang.Object).notifyAll()
            }
        }

        /**
         * Sets the exception from failed execution and executes callback if available. Notifies any
         * thread waiting for completion.
         *
         * @param exception exception of the failed task
         */
        internal fun setException(exception: Exception) {
            this.exception = exception
            this.state = FAILED
            if (hasCallback()) {
                callback!!.onError(exception)
            }
            synchronized(lock) {
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                (lock as java.lang.Object).notifyAll()
            }
        }

        @Throws(ExecutionException::class)
        override fun getValue(): T =
            when (state) {
                COMPLETED -> {
                    @Suppress("UNCHECKED_CAST")
                    value as T
                }
                FAILED -> throw ExecutionException(exception)
                else -> throw IllegalStateException("Execution not completed yet")
            }

        @Throws(InterruptedException::class)
        override fun await() {
            synchronized(lock) {
                while (!isCompleted) {
                    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                    (lock as java.lang.Object).wait()
                }
            }
        }

        companion object {
            const val RUNNING = 1
            const val FAILED = 2
            const val COMPLETED = 3
        }
    }
}