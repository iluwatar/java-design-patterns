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
// ABOUTME: Test class for AsynchronousHealthChecker component.
// ABOUTME: Verifies async health check execution, timeouts, exceptions, and shutdown behavior.
package com.iluwatar.health.check

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.github.oshai.kotlinlogging.KotlinLogging
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

private val logger = KotlinLogging.logger {}

/** Tests for [AsynchronousHealthChecker]. */
class AsynchronousHealthCheckerTest {

    /** The [AsynchronousHealthChecker] instance to be tested. */
    private lateinit var healthChecker: AsynchronousHealthChecker

    private lateinit var listAppender: ListAppender<ILoggingEvent>

    private val executorService: ScheduledExecutorService = mockk()

    /**
     * Sets up the test environment before each test method.
     *
     * Creates a new [AsynchronousHealthChecker] instance.
     */
    @BeforeEach
    fun setUp() {
        healthChecker = AsynchronousHealthChecker()
        // Replace the logger with the root logger of logback
        LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)

        // Create and start a ListAppender
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        listAppender = ListAppender()
        listAppender.start()

        // Add the appender to the root logger context
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(listAppender)
    }

    /**
     * Tears down the test environment after each test method.
     *
     * Shuts down the [AsynchronousHealthChecker] instance to prevent resource leaks.
     */
    @AfterEach
    fun tearDown() {
        healthChecker.shutdown()
        (LoggerFactory.getILoggerFactory() as LoggerContext).reset()
    }

    /**
     * Tests that the performCheck() method completes normally when the health supplier
     * returns a successful health check.
     *
     * Given a health supplier that returns a healthy status, the test verifies that the
     * performCheck() method completes normally and returns the expected health object.
     */
    @Test
    fun whenPerformCheck_thenCompletesNormally() {
        // Given
        val healthSupplier = Supplier { Health.up().build() }

        // When
        val healthFuture = healthChecker.performCheck(healthSupplier, 3)

        // Then
        val health = healthFuture.get()
        assertEquals(Health.up().build(), health)
    }

    /**
     * Tests that the performCheck() method returns a healthy health status when the health
     * supplier returns a healthy status.
     *
     * Given a health supplier that returns a healthy status, the test verifies that the
     * performCheck() method returns a health object with a status of UP.
     */
    @Test
    fun whenHealthCheckIsSuccessful_ReturnsHealthy() {
        // Arrange
        val healthSupplier = Supplier { Health.up().build() }

        // Act
        val healthFuture = healthChecker.performCheck(healthSupplier, 4)

        // Assert
        assertEquals(Status.UP, healthFuture.get().status)
    }

    /**
     * Tests that the performCheck() method rejects new tasks after the shutdown()
     * method is called.
     *
     * Given the [AsynchronousHealthChecker] instance is shut down, the test verifies that
     * the performCheck() method throws a [RejectedExecutionException] when attempting
     * to submit a new health check task.
     */
    @Test
    fun whenShutdown_thenRejectsNewTasks() {
        // Given
        healthChecker.shutdown()

        // When/Then
        assertThrows(RejectedExecutionException::class.java) {
            healthChecker.performCheck(Supplier { Health.up().build() }, 2)
        }
    }

    /**
     * Tests that the performCheck() method returns a healthy health status when the health
     * supplier returns a healthy status.
     *
     * Given a health supplier that throws a RuntimeException, the test verifies that the
     * performCheck() method returns a health object with a status of DOWN and an error message
     * containing the exception message.
     */
    @Test
    fun whenHealthCheckThrowsException_thenReturnsDown() {
        // Arrange
        val healthSupplier = Supplier<Health> {
            throw RuntimeException("Health check failed")
        }
        // Act
        val healthFuture = healthChecker.performCheck(healthSupplier, 10)
        // Assert
        val health = healthFuture.join()
        assertEquals(Status.DOWN, health.status)
        val errorMessage = health.details["error"].toString()
        assertTrue(errorMessage.contains("Health check failed"))
    }

    /**
     * Helper method to check if the log contains a specific message.
     *
     * @param action The action that triggers the log statement.
     * @return True if the log contains the message after the action is performed, false otherwise.
     */
    private fun doesLogContainMessage(action: () -> Unit): Boolean {
        action()
        val events = listAppender.list
        return events.any { event -> event.message.contains("Health check executor did not terminate") }
    }

    /**
     * Tests that the [AsynchronousHealthChecker.shutdown] method logs an error message when
     * the executor does not terminate after attempting to cancel tasks.
     */
    @Test
    fun whenShutdownExecutorDoesNotTerminateAfterCanceling_LogsErrorMessage() {
        // Given
        healthChecker.shutdown() // To trigger the scenario

        // When/Then
        val containsMessage = doesLogContainMessage { healthChecker.shutdown() }
        if (!containsMessage) {
            val events = listAppender.list
            logger.info { "Logged events:" }
            for (event in events) {
                logger.info { event.message }
            }
        }
        assertTrue(containsMessage, "Expected log message not found")
    }

    /**
     * Verifies that [AsynchronousHealthChecker.awaitTerminationWithTimeout] returns true even
     * if the executor service does not terminate completely within the specified timeout.
     */
    @Test
    fun awaitTerminationWithTimeout_IncompleteTermination_ReturnsTrue() {
        // Mock executor service to return false (incomplete termination)
        every { executorService.awaitTermination(5, TimeUnit.SECONDS) } returns false

        // Use reflection to access the private method for code coverage.
        val privateMethod = AsynchronousHealthChecker::class.java.getDeclaredMethod("awaitTerminationWithTimeout")
        privateMethod.isAccessible = true

        // When
        val result = privateMethod.invoke(healthChecker) as Boolean

        // Then
        assertTrue(result, "Termination should be incomplete")
    }
}
