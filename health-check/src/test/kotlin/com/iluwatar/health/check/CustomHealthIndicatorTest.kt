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
// ABOUTME: Test class for CustomHealthIndicator component.
// ABOUTME: Verifies database health checks, caching, and cache eviction behavior.
package com.iluwatar.health.check

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

/** Tests class for [CustomHealthIndicator]. */
class CustomHealthIndicatorTest {

    /** Mocked AsynchronousHealthChecker instance. */
    private lateinit var healthChecker: AsynchronousHealthChecker

    /** Mocked CacheManager instance. */
    private lateinit var cacheManager: CacheManager

    /** Mocked HealthCheckRepository instance. */
    private lateinit var healthCheckRepository: HealthCheckRepository

    /** Mocked Cache instance. */
    private lateinit var cache: Cache

    /** `CustomHealthIndicator` instance to be tested. */
    private lateinit var customHealthIndicator: CustomHealthIndicator

    /** Sets up the test environment. */
    @BeforeEach
    fun setUp() {
        healthChecker = mockk()
        cacheManager = mockk()
        healthCheckRepository = mockk()
        cache = mockk()

        every { cacheManager.getCache("health-check") } returns cache
        customHealthIndicator = CustomHealthIndicator(healthChecker, cacheManager, healthCheckRepository)
    }

    /**
     * Test case for the `health()` method when the database is up.
     *
     * Asserts that when the `health()` method is called and the database is up, it returns a
     * Health object with Status.UP.
     */
    @Test
    fun whenDatabaseIsUp_thenHealthIsUp() {
        val future = CompletableFuture.completedFuture(
            Health.up().withDetail("database", "reachable").build()
        )
        every { healthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future
        every { healthCheckRepository.checkHealth() } returns 1

        val health = customHealthIndicator.health()

        assertEquals(Status.UP, health.status)
    }

    /**
     * Test case for the `health()` method when the database is down.
     *
     * Asserts that when the `health()` method is called and the database is down, it returns a
     * Health object with Status.DOWN.
     */
    @Test
    fun whenDatabaseIsDown_thenHealthIsDown() {
        val future = CompletableFuture.completedFuture(
            Health.down().withDetail("database", "unreachable").build()
        )
        every { healthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future
        every { healthCheckRepository.checkHealth() } returns null

        val health = customHealthIndicator.health()

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
        every { healthChecker.performCheck(any<Supplier<Health>>(), any()) } returns future

        val health = customHealthIndicator.health()

        assertEquals(Status.DOWN, health.status)
    }

    /**
     * Test case for the `evictHealthCache()` method.
     *
     * Asserts that when the `evictHealthCache()` method is called, the health cache is cleared.
     */
    @Test
    fun whenEvictHealthCache_thenCacheIsCleared() {
        every { cache.clear() } just runs

        customHealthIndicator.evictHealthCache()

        verify(exactly = 1) { cache.clear() }
        verify(exactly = 1) { cacheManager.getCache("health-check") }
    }

    /** Configuration static class for the health check cache. */
    @Configuration
    class CacheConfig {
        /**
         * Creates a concurrent map cache manager named "health-check".
         *
         * @return a new ConcurrentMapCacheManager instance
         */
        @Bean
        fun cacheManager(): CacheManager {
            return ConcurrentMapCacheManager("health-check")
        }
    }
}
