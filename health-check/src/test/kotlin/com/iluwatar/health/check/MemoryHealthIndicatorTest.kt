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
// ABOUTME: Test class for MemoryHealthIndicator component.
// ABOUTME: Verifies memory health status based on threshold, interruption, and failure scenarios.
package com.iluwatar.health.check

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.function.Supplier

/** Unit tests for [MemoryHealthIndicator]. */
class MemoryHealthIndicatorTest {

    /** Mocked AsynchronousHealthChecker instance. */
    private val asynchronousHealthChecker: AsynchronousHealthChecker = mockk()

    /** `MemoryHealthIndicator` instance to be tested. */
    private val memoryHealthIndicator = MemoryHealthIndicator(asynchronousHealthChecker)

    /**
     * Test case for the `health()` method when memory usage is below the threshold.
     *
     * Asserts that when the `health()` method is called and memory usage is below the threshold,
     * it returns a Health object with Status.UP.
     */
    @Test
    fun whenMemoryUsageIsBelowThreshold_thenHealthIsUp() {
        // Arrange
        val future = CompletableFuture.completedFuture(
            Health.up().withDetail("memory usage", "50% of max").build()
        )
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future

        // Act
        val health = memoryHealthIndicator.health()

        // Assert
        assertEquals(Status.UP, health.status)
        assertEquals("50% of max", health.details["memory usage"])
    }

    /**
     * Test case for the `health()` method when memory usage is above the threshold.
     *
     * Asserts that when the `health()` method is called and memory usage is above the threshold,
     * it returns a Health object with Status.DOWN.
     */
    @Test
    fun whenMemoryUsageIsAboveThreshold_thenHealthIsDown() {
        // Arrange
        val future = CompletableFuture.completedFuture(
            Health.down().withDetail("memory usage", "95% of max").build()
        )
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future

        // Act
        val health = memoryHealthIndicator.health()

        // Assert
        assertEquals(Status.DOWN, health.status)
        assertEquals("95% of max", health.details["memory usage"])
    }

    /**
     * Test case for the `health()` method when the health check is interrupted.
     *
     * Asserts that when the `health()` method is called and the health check is interrupted, it
     * returns a Health object with Status DOWN and an error detail indicating the interruption.
     */
    @Test
    fun whenHealthCheckIsInterrupted_thenHealthIsDown() {
        // Arrange
        val future: CompletableFuture<Health> = mockk()
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future
        // Simulate InterruptedException when future.get() is called
        every { future.get() } throws InterruptedException("Health check interrupted")

        // Act
        val health = memoryHealthIndicator.health()

        // Assert
        assertEquals(Status.DOWN, health.status)
        val errorDetail = health.details["error"] as? String
        assertNotNull(errorDetail)
        assertTrue(errorDetail!!.contains("Health check interrupted"))
    }

    /**
     * Test case for the `health()` method when the health check execution fails.
     *
     * Asserts that when the `health()` method is called and the health check execution fails, it
     * returns a Health object with Status DOWN and an error detail indicating the failure.
     */
    @Test
    fun whenHealthCheckExecutionFails_thenHealthIsDown() {
        // Arrange
        val future = CompletableFuture<Health>()
        future.completeExceptionally(
            ExecutionException(RuntimeException("Service unavailable"))
        )
        every { asynchronousHealthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future

        // Act
        val health = memoryHealthIndicator.health()

        // Assert
        assertEquals(Status.DOWN, health.status)
        assertTrue(health.details["error"].toString().contains("Service unavailable"))
    }
}
