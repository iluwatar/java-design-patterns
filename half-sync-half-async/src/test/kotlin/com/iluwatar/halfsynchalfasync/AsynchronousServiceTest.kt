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

// ABOUTME: Tests the AsynchronousService class for correct task execution behavior.
// ABOUTME: Verifies successful execution, error handling during call, and error handling during pre-call.
package com.iluwatar.halfsynchalfasync

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.mockk.confirmVerified
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue

/** AsynchronousServiceTest */
class AsynchronousServiceTest {
    private lateinit var service: AsynchronousService
    private lateinit var task: AsyncTask<Any>

    @BeforeEach
    fun setUp() {
        service = AsynchronousService(LinkedBlockingQueue())
        task = mockk(relaxed = true)
    }

    @Test
    fun testPerfectExecution() {
        val result = Any()
        every { task.call() } returns result

        service.execute(task)

        // Wait for async execution to complete
        Thread.sleep(2000)

        verifyOrder {
            task.onPreCall()
            task.call()
            task.onPostCall(result)
        }

        confirmVerified(task)
    }

    @Test
    fun testCallException() {
        val exception = IOException()
        every { task.call() } throws exception

        service.execute(task)

        // Wait for async execution to complete
        Thread.sleep(2000)

        verifyOrder {
            task.onPreCall()
            task.call()
            task.onError(exception)
        }

        confirmVerified(task)
    }

    @Test
    fun testPreCallException() {
        val exception = IllegalStateException()
        every { task.onPreCall() } throws exception

        service.execute(task)

        // Wait for async execution to complete
        Thread.sleep(2000)

        verifyOrder {
            task.onPreCall()
            task.onError(exception)
        }

        confirmVerified(task)
    }
}
