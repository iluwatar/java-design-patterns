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

// ABOUTME: Promise implementation that supports async fulfillment, chaining, and error handling.
// ABOUTME: Extends PromiseSupport with thenAccept, thenApply, and onError for composable async workflows.
package com.iluwatar.promise

import java.util.concurrent.Callable
import java.util.concurrent.Executor

/**
 * A Promise represents a proxy for a value not necessarily known when the promise is created. It
 * allows you to associate dependent promises to an asynchronous action's eventual success value or
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead of
 * the final value, the asynchronous method returns a promise of having a value at some point in the
 * future.
 *
 * @param T type of result.
 */
class Promise<T> : PromiseSupport<T>() {

    private var fulfillmentAction: Runnable? = null
    private var exceptionHandler: ((Throwable) -> Unit)? = null

    /**
     * Fulfills the promise with the provided value.
     *
     * @param value the fulfilled value that can be accessed using [get].
     */
    override fun fulfill(value: T) {
        super.fulfill(value)
        postFulfillment()
    }

    /**
     * Fulfills the promise with exception due to error in execution.
     *
     * @param exception the exception will be wrapped in [java.util.concurrent.ExecutionException]
     *     when accessing the value using [get].
     */
    override fun fulfillExceptionally(exception: Exception) {
        super.fulfillExceptionally(exception)
        handleException(exception)
        postFulfillment()
    }

    private fun handleException(exception: Exception) {
        exceptionHandler?.invoke(exception)
    }

    private fun postFulfillment() {
        fulfillmentAction?.run()
    }

    /**
     * Executes the task using the executor in other thread and fulfills the promise returned once the
     * task completes either successfully or with an exception.
     *
     * @param task the task that will provide the value to fulfill the promise.
     * @param executor the executor in which the task should be run.
     * @return a promise that represents the result of running the task provided.
     */
    fun fulfillInAsync(task: Callable<T>, executor: Executor): Promise<T> {
        executor.execute {
            try {
                fulfill(task.call())
            } catch (ex: Exception) {
                fulfillExceptionally(ex)
            }
        }
        return this
    }

    /**
     * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with result
     * of this promise as argument to the action provided.
     *
     * @param action action to be executed.
     * @return a new promise.
     */
    fun thenAccept(action: (T) -> Unit): Promise<Void?> {
        val dest = Promise<Void?>()
        fulfillmentAction = ConsumeAction(this, dest, action)
        return dest
    }

    /**
     * Set the exception handler on this promise.
     *
     * @param exceptionHandler a handler that will handle the exception occurred while fulfilling the
     *     promise.
     * @return this
     */
    fun onError(exceptionHandler: (Throwable) -> Unit): Promise<T> {
        this.exceptionHandler = exceptionHandler
        return this
    }

    /**
     * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with result
     * of this promise as argument to the function provided.
     *
     * @param func function to be executed.
     * @return a new promise.
     */
    fun <V> thenApply(func: (T) -> V): Promise<V> {
        val dest = Promise<V>()
        fulfillmentAction = TransformAction(this, dest, func)
        return dest
    }

    /**
     * Accesses the value from source promise and calls the consumer, then fulfills the destination
     * promise.
     */
    private class ConsumeAction<T>(
        private val src: Promise<T>,
        private val dest: Promise<Void?>,
        private val action: (T) -> Unit,
    ) : Runnable {
        override fun run() {
            try {
                action(src.get())
                dest.fulfill(null)
            } catch (throwable: Throwable) {
                dest.fulfillExceptionally(throwable.cause as Exception)
            }
        }
    }

    /**
     * Accesses the value from source promise, then fulfills the destination promise using the
     * transformed value. The source value is transformed using the transformation function.
     */
    private class TransformAction<T, V>(
        private val src: Promise<T>,
        private val dest: Promise<V>,
        private val func: (T) -> V,
    ) : Runnable {
        override fun run() {
            try {
                dest.fulfill(func(src.get()))
            } catch (throwable: Throwable) {
                dest.fulfillExceptionally(throwable.cause as Exception)
            }
        }
    }
}
