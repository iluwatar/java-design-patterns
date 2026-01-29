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

// ABOUTME: Defines the executor interface for starting and managing async tasks.
// ABOUTME: Provides methods to start tasks with optional callbacks and to block until completion.
package com.iluwatar.async.method.invocation

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException

/**
 * AsyncExecutor interface.
 */
interface AsyncExecutor {
    /**
     * Starts processing of an async task. Returns immediately with async result.
     *
     * @param task task to be executed asynchronously
     * @return async result for the task
     */
    fun <T> startProcess(task: Callable<T>?): AsyncResult<T>

    /**
     * Starts processing of an async task. Returns immediately with async result. Executes callback
     * when the task is completed.
     *
     * @param task task to be executed asynchronously
     * @param callback callback to be executed on task completion
     * @return async result for the task
     */
    fun <T> startProcess(
        task: Callable<T>?,
        callback: AsyncCallback<T>?,
    ): AsyncResult<T>

    /**
     * Ends processing of an async task. Blocks the current thread if necessary and returns the
     * evaluated value of the completed task.
     *
     * @param asyncResult async result of a task
     * @return evaluated value of the completed task
     * @throws ExecutionException if execution has failed, containing the root cause
     * @throws InterruptedException if the execution is interrupted
     */
    @Throws(ExecutionException::class, InterruptedException::class)
    fun <T> endProcess(asyncResult: AsyncResult<T>): T
}