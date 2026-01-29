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
package com.iluwatar.singleton

// ABOUTME: Abstract base test class for singleton pattern implementations.
// ABOUTME: Verifies same-thread identity, multi-threaded identity, and reflection protection.

import java.lang.reflect.InvocationTargetException
import java.time.Duration.ofMillis
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Test

/**
 * This class provides several test cases that test singleton construction.
 *
 * The first proves that multiple calls to the singleton getInstance object are the same when
 * called in the SAME thread. The second proves that multiple calls to the singleton getInstance
 * object are the same when called in DIFFERENT threads.
 *
 * @param S The singleton type under test
 * @param singletonInstanceMethod A supplier function returning the singleton instance
 */
abstract class SingletonTest<S>(
    private val singletonInstanceMethod: () -> S
) {

    /** Test the singleton in a non-concurrent setting. */
    @Test
    fun testMultipleCallsReturnTheSameObjectInSameThread() {
        // Create several instances in the same calling thread
        val instance1 = singletonInstanceMethod()
        val instance2 = singletonInstanceMethod()
        val instance3 = singletonInstanceMethod()
        // now check they are equal
        assertSame(instance1, instance2)
        assertSame(instance1, instance3)
        assertSame(instance2, instance3)
    }

    /** Test singleton instance in a concurrent setting. */
    @Test
    fun testMultipleCallsReturnTheSameObjectInDifferentThreads() {
        assertTimeout(
            ofMillis(10000)
        ) {
            // Create 10000 tasks and inside each callable instantiate the singleton class
            val tasks = (0 until 10000).map {
                Callable { singletonInstanceMethod() }
            }.toMutableList()

            // Use up to 8 concurrent threads to handle the tasks
            val executorService = Executors.newFixedThreadPool(8)
            val results = executorService.invokeAll(tasks)

            // wait for all the threads to complete
            val expectedInstance = singletonInstanceMethod()
            for (res in results) {
                val instance = res.get()
                assertNotNull(instance)
                assertSame(expectedInstance, instance)
            }

            // tidy up the executor
            executorService.shutdown()
        }
    }

    /** Test creating new instance by reflection. */
    @Test
    open fun testCreatingNewInstanceByReflection() {
        val firstTimeInstantiated = singletonInstanceMethod()
        val constructor = firstTimeInstantiated!!::class.java.getDeclaredConstructor()
        constructor.isAccessible = true
        assertThrows(InvocationTargetException::class.java) { constructor.newInstance() }
    }
}
