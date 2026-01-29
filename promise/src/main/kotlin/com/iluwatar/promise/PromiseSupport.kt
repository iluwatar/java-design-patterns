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

// ABOUTME: Simplified Future implementation that supports fulfillment with a value or exception.
// ABOUTME: Provides the underlying synchronization mechanism for the Promise pattern.
package com.iluwatar.promise

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * A really simplified implementation of future that allows completing it successfully with a value
 * or exceptionally with an exception.
 */
open class PromiseSupport<T> : Future<T> {

    private val lock = Any()

    @Volatile
    private var state = RUNNING
    private var value: T? = null
    private var exception: Exception? = null

    internal open fun fulfill(value: T) {
        this.value = value
        this.state = COMPLETED
        synchronized(lock) {
            (lock as java.lang.Object).notifyAll()
        }
    }

    internal open fun fulfillExceptionally(exception: Exception) {
        this.exception = exception
        this.state = FAILED
        synchronized(lock) {
            (lock as java.lang.Object).notifyAll()
        }
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override fun isCancelled(): Boolean = false

    override fun isDone(): Boolean = state > RUNNING

    @Throws(InterruptedException::class, ExecutionException::class)
    override fun get(): T {
        synchronized(lock) {
            while (state == RUNNING) {
                (lock as java.lang.Object).wait()
            }
        }
        if (state == COMPLETED) {
            @Suppress("UNCHECKED_CAST")
            return value as T
        }
        throw ExecutionException(exception)
    }

    @Throws(ExecutionException::class)
    override fun get(timeout: Long, unit: TimeUnit): T {
        synchronized(lock) {
            while (state == RUNNING) {
                try {
                    (lock as java.lang.Object).wait(unit.toMillis(timeout))
                } catch (e: InterruptedException) {
                    logger.warn(e) { "Interrupted!" }
                    Thread.currentThread().interrupt()
                }
            }
        }
        if (state == COMPLETED) {
            @Suppress("UNCHECKED_CAST")
            return value as T
        }
        throw ExecutionException(exception)
    }

    companion object {
        private const val RUNNING = 1
        private const val FAILED = 2
        private const val COMPLETED = 3
    }
}
