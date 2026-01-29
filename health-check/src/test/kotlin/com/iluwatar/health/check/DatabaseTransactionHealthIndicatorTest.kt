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
// ABOUTME: Test class for DatabaseTransactionHealthIndicator component.
// ABOUTME: Verifies database transaction success, failure, and timeout scenarios.
package com.iluwatar.health.check

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import org.springframework.retry.support.RetryTemplate
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

/** Unit tests for the [DatabaseTransactionHealthIndicator] class. */
class DatabaseTransactionHealthIndicatorTest {

    /** Timeout value in seconds for the health check. */
    private val timeoutInSeconds = 4L

    /** Mocked HealthCheckRepository instance. */
    private lateinit var healthCheckRepository: HealthCheckRepository

    /** Mocked AsynchronousHealthChecker instance. */
    private lateinit var asynchronousHealthChecker: AsynchronousHealthChecker

    /** Mocked RetryTemplate instance. */
    private lateinit var retryTemplate: RetryTemplate

    /** `DatabaseTransactionHealthIndicator` instance to be tested. */
    private lateinit var healthIndicator: DatabaseTransactionHealthIndicator

    /** Performs initialization before each test method. */
    @BeforeEach
    fun setUp() {
        healthCheckRepository = mockk()
        asynchronousHealthChecker = mockk()
        retryTemplate = mockk()
        healthIndicator = DatabaseTransactionHealthIndicator(
            healthCheckRepository, asynchronousHealthChecker, retryTemplate
        )
        healthIndicator.timeoutInSeconds = timeoutInSeconds
    }

    /**
     * Test case for the `health()` method when the database transaction succeeds.
     *
     * Asserts that when the `health()` method is called and the database transaction succeeds, it
     * returns a Health object with Status.UP.
     */
    @Test
    fun whenDatabaseTransactionSucceeds_thenHealthIsUp() {
        val future = CompletableFuture.completedFuture(Health.up().build())
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), eq(timeoutInSeconds)) } returns future

        // Simulate the health check repository behavior
        every { healthCheckRepository.performTestTransaction() } just runs

        // Now call the actual method
        val health = healthIndicator.health()

        // Check that the health status is UP
        assertEquals(Status.UP, health.status)
    }

    /**
     * Test case for the `health()` method when the database transaction fails.
     *
     * Asserts that when the `health()` method is called and the database transaction fails, it
     * returns a Health object with Status.DOWN.
     */
    @Test
    fun whenDatabaseTransactionFails_thenHealthIsDown() {
        val future = CompletableFuture<Health>()
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), eq(timeoutInSeconds)) } returns future

        // Simulate a database exception during the transaction
        every { healthCheckRepository.performTestTransaction() } throws RuntimeException("DB exception")

        // Complete the future exceptionally to simulate a failure in the health check
        future.completeExceptionally(RuntimeException("DB exception"))

        val health = healthIndicator.health()

        // Check that the health status is DOWN
        assertEquals(Status.DOWN, health.status)
    }

    /**
     * Test case for the `health()` method when the health check times out.
     *
     * Asserts that when the `health()` method is called and the health check times out, it returns
     * a Health object with Status.DOWN.
     */
    @Test
    fun whenHealthCheckTimesOut_thenHealthIsDown() {
        val future = CompletableFuture<Health>()
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), eq(timeoutInSeconds)) } returns future

        // Complete the future exceptionally to simulate a timeout
        future.completeExceptionally(RuntimeException("Simulated timeout"))

        val health = healthIndicator.health()

        // Check that the health status is DOWN due to timeout
        assertEquals(Status.DOWN, health.status)
    }
}
