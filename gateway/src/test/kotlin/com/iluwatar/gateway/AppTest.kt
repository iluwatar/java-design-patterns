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
package com.iluwatar.gateway

// ABOUTME: Tests for the Gateway pattern verifying each service executes without exceptions.
// ABOUTME: Uses an ExecutorService to test asynchronous execution of gateway services.

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AppTest {

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
    fun testServiceAExecution() {
        // Test Service A execution
        val serviceAFuture = executorService.submit {
            try {
                val serviceA = gatewayFactory.getGateway("ServiceA")
                serviceA?.execute()
            } catch (e: Exception) {
                fail("Service A should not throw an exception.")
            }
        }

        // Wait for Service A to complete
        serviceAFuture.get()
    }

    @Test
    fun testServiceCExecutionWithException() {
        // Test Service B execution with an exception
        val serviceBFuture = executorService.submit {
            try {
                val serviceB = gatewayFactory.getGateway("ServiceB")
                serviceB?.execute()
            } catch (e: Exception) {
                fail("Service B should not throw an exception.")
            }
        }

        // Wait for Service B to complete
        serviceBFuture.get()
    }

    @Test
    fun testServiceCExecution() {
        // Test Service C execution
        val serviceCFuture = executorService.submit {
            try {
                val serviceC = gatewayFactory.getGateway("ServiceC")
                serviceC?.execute()
            } catch (e: Exception) {
                fail("Service C should not throw an exception.")
            }
        }

        // Wait for Service C to complete
        serviceCFuture.get()
    }

    @Test
    fun testServiceCError() {
        try {
            val serviceC = gatewayFactory.getGateway("ServiceC") as ExternalServiceC
            serviceC.error()
            fail("Service C should throw an exception.")
        } catch (e: Exception) {
            assertEquals("Service C encountered an error", e.message)
        }
    }
}
