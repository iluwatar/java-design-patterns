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
// ABOUTME: Test class for HealthCheckRepository component.
// ABOUTME: Verifies database connection checks and test transaction execution.
package com.iluwatar.health.check

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Tests class for [HealthCheckRepository]. */
class HealthCheckRepositoryTest {

    /** Mocked EntityManager instance. */
    private lateinit var entityManager: EntityManager

    /** `HealthCheckRepository` instance to be tested. */
    private lateinit var healthCheckRepository: HealthCheckRepository

    @BeforeEach
    fun setUp() {
        entityManager = mockk()
        healthCheckRepository = HealthCheckRepository()

        // Use reflection to inject the mocked entityManager
        val field = HealthCheckRepository::class.java.getDeclaredField("entityManager")
        field.isAccessible = true
        field.set(healthCheckRepository, entityManager)
    }

    /**
     * Test case for the `performTestTransaction()` method.
     *
     * Asserts that when the `performTestTransaction()` method is called, it successfully executes
     * a test transaction.
     */
    @Test
    fun whenCheckHealth_thenReturnsOne() {
        // Arrange
        val mockedQuery: Query = mockk()
        every { entityManager.createNativeQuery("SELECT 1") } returns mockedQuery
        every { mockedQuery.singleResult } returns 1

        // Act
        val healthCheckResult = healthCheckRepository.checkHealth()

        // Assert
        assertNotNull(healthCheckResult)
        assertEquals(1, healthCheckResult)
    }

    /**
     * Test case for the `performTestTransaction()` method.
     *
     * Asserts that when the `performTestTransaction()` method is called, it successfully executes
     * a test transaction.
     */
    @Test
    fun whenPerformTestTransaction_thenSucceeds() {
        // Arrange
        val healthCheck = HealthCheck(status = "OK")

        // Mocking the necessary EntityManager behaviors
        every { entityManager.persist(any<HealthCheck>()) } just runs
        every { entityManager.flush() } just runs
        every { entityManager.find(eq(HealthCheck::class.java), any()) } returns healthCheck
        every { entityManager.remove(any<HealthCheck>()) } just runs

        // Act & Assert
        assertDoesNotThrow { healthCheckRepository.performTestTransaction() }

        // Verify the interactions
        verify { entityManager.persist(any<HealthCheck>()) }
        verify { entityManager.flush() }
        verify { entityManager.remove(any<HealthCheck>()) }
    }

    /**
     * Test case for the `checkHealth()` method when the database is down.
     *
     * Asserts that when the `checkHealth()` method is called and the database is down, it throws a
     * RuntimeException.
     */
    @Test
    fun whenCheckHealth_andDatabaseIsDown_thenThrowsException() {
        // Arrange
        every { entityManager.createNativeQuery("SELECT 1") } throws RuntimeException()

        // Act & Assert
        assertThrows(RuntimeException::class.java) { healthCheckRepository.checkHealth() }
    }

    /**
     * Test case for the `performTestTransaction()` method when the persist operation fails.
     *
     * Asserts that when the `performTestTransaction()` method is called and the persist operation
     * fails, it throws a RuntimeException.
     */
    @Test
    fun whenPerformTestTransaction_andPersistFails_thenThrowsException() {
        // Arrange
        every { entityManager.persist(any<HealthCheck>()) } throws RuntimeException()

        // Act & Assert
        assertThrows(RuntimeException::class.java) { healthCheckRepository.performTestTransaction() }

        // Verify that remove is not called if persist fails
        verify(exactly = 0) { entityManager.remove(any<HealthCheck>()) }
    }
}
