/*
 * This project is licensed under the MIT license. Module model-view-viewmodel
 * is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright (c) 2014-2022 Ilkka Seppala
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
package com.iluwatar.gateway

// ABOUTME: Tests for GatewayFactory verifying registration, retrieval, and concurrent access.
// ABOUTME: Validates type-safe gateway lookup, null handling, and thread-safe execution.

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ServiceFactoryTest {

    private lateinit var gatewayFactory: GatewayFactory
    private lateinit var executorService: ExecutorService

    @BeforeEach
    fun setUp() {
        gatewayFactory = GatewayFactory()
        executorService = Executors.newFixedThreadPool(2)
        gatewayFactory.registerGateway("ServiceA", ExternalServiceA())
        gatewayFactory.registerGateway("ServiceB", ExternalServiceB())
        gatewayFactory.registerGateway("ServiceC", ExternalServiceC())
    }

    @Test
    fun testGatewayFactoryRegistrationAndRetrieval() {
        val serviceA = gatewayFactory.getGateway("ServiceA")
        val serviceB = gatewayFactory.getGateway("ServiceB")
        val serviceC = gatewayFactory.getGateway("ServiceC")

        // Check if the retrieved instances match their expected types
        assertTrue(
            serviceA is ExternalServiceA,
            "ServiceA should be an instance of ExternalServiceA",
        )
        assertTrue(
            serviceB is ExternalServiceB,
            "ServiceB should be an instance of ExternalServiceB",
        )
        assertTrue(
            serviceC is ExternalServiceC,
            "ServiceC should be an instance of ExternalServiceC",
        )
    }

    @Test
    fun testGatewayFactoryRegistrationWithNonExistingKey() {
        val nonExistingService = gatewayFactory.getGateway("NonExistingService")
        assertNull(nonExistingService)
    }

    @Test
    fun testGatewayFactoryConcurrency() {
        val numThreads = 10
        val latch = CountDownLatch(numThreads)
        val failed = AtomicBoolean(false)

        for (i in 0 until numThreads) {
            executorService.submit {
                try {
                    val serviceA = gatewayFactory.getGateway("ServiceA")
                    serviceA?.execute()
                } catch (e: Exception) {
                    failed.set(true)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        assertFalse(failed.get(), "This should not fail")
    }
}
