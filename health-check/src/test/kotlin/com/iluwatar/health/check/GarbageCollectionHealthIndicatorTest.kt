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
// ABOUTME: Test class for GarbageCollectionHealthIndicator component.
// ABOUTME: Verifies GC health status reporting and memory usage warnings.
package com.iluwatar.health.check

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.actuate.health.Status
import java.lang.management.GarbageCollectorMXBean
import java.lang.management.MemoryPoolMXBean
import java.lang.management.MemoryUsage
import java.util.Locale

/** Test class for [GarbageCollectionHealthIndicator]. */
class GarbageCollectionHealthIndicatorTest {

    /** Mocked garbage collector MXBean. */
    private lateinit var garbageCollectorMXBean: GarbageCollectorMXBean

    /** Mocked memory pool MXBean. */
    private lateinit var memoryPoolMXBean: MemoryPoolMXBean

    /** Garbage collection health indicator instance to be tested. */
    private lateinit var healthIndicator: GarbageCollectionHealthIndicator

    /** Set up the test environment before each test case. */
    @BeforeEach
    fun setUp() {
        garbageCollectorMXBean = mockk()
        memoryPoolMXBean = mockk()
        healthIndicator = spyk(object : GarbageCollectionHealthIndicator() {
            override fun getGarbageCollectorMxBeans(): List<GarbageCollectorMXBean> {
                return listOf(garbageCollectorMXBean)
            }

            override fun getMemoryPoolMxBeans(): List<MemoryPoolMXBean> {
                return listOf(memoryPoolMXBean)
            }
        })
        healthIndicator.memoryUsageThreshold = 0.8
        Locale.setDefault(Locale.US)
    }

    /** Test case to verify that the health status is up when memory usage is low. */
    @Test
    fun whenMemoryUsageIsLow_thenHealthIsUp() {
        every { garbageCollectorMXBean.collectionCount } returns 100L
        every { garbageCollectorMXBean.collectionTime } returns 1000L
        every { garbageCollectorMXBean.memoryPoolNames } returns arrayOf("Eden Space")
        every { garbageCollectorMXBean.name } returns "G1 Young Generation"

        every { memoryPoolMXBean.usage } returns MemoryUsage(0, 100, 500, 1000)
        every { memoryPoolMXBean.name } returns "Eden Space"

        val health = healthIndicator.health()
        assertEquals(Status.UP, health.status)
    }

    /** Test case to verify that the health status contains a warning when memory usage is high. */
    @Test
    fun whenMemoryUsageIsHigh_thenHealthContainsWarning() {
        // Arrange
        val threshold = 0.8 // 80% threshold for test
        healthIndicator.memoryUsageThreshold = threshold

        val poolName = "CodeCache"
        every { garbageCollectorMXBean.name } returns "G1 Young Generation"
        every { garbageCollectorMXBean.memoryPoolNames } returns arrayOf(poolName)
        every { garbageCollectorMXBean.collectionCount } returns 100L
        every { garbageCollectorMXBean.collectionTime } returns 1000L

        val maxMemory = 1000L // e.g., 1000 bytes
        val usedMemory = (threshold * maxMemory).toLong() + 1 // e.g., 801 bytes to exceed 80% threshold
        every { memoryPoolMXBean.usage } returns MemoryUsage(0, usedMemory, usedMemory, maxMemory)
        every { memoryPoolMXBean.name } returns poolName

        // Act
        val health = healthIndicator.health()

        // Assert
        @Suppress("UNCHECKED_CAST")
        val gcDetails = health.details["G1 Young Generation"] as? Map<String, Any>
        assertNotNull(gcDetails, "Expected details for 'G1 Young Generation', but none were found.")

        val memoryPoolsDetail = gcDetails?.get("memoryPools") as? String
        assertNotNull(
            memoryPoolsDetail,
            "Expected memory pool details for 'CodeCache', but none were found."
        )

        // Extracting the actual usage reported in the details for comparison
        val memoryUsageReported = memoryPoolsDetail!!.split(": ")[1].trim().replace("%", "")
        val memoryUsagePercentage = memoryUsageReported.toDouble()

        assertTrue(
            memoryUsagePercentage > threshold,
            "Memory usage percentage should be above the threshold."
        )

        val warning = gcDetails["warning"] as? String
        assertNotNull(warning, "Expected a warning for high memory usage, but none was found.")

        // Check that the warning message is as expected
        val expectedWarningRegex = "Memory pool '$poolName' usage is high \\([\\d.,\\s]+%\\)".toRegex()
        assertTrue(
            warning!!.matches(expectedWarningRegex),
            "Expected a high usage warning, but format is incorrect: $warning"
        )
    }

    /** Test case to verify that the health status is up when there are no garbage collections. */
    @Test
    fun whenNoGarbageCollections_thenHealthIsUp() {
        // Arrange: Mock the garbage collector to simulate no collections
        every { garbageCollectorMXBean.collectionCount } returns 0L
        every { garbageCollectorMXBean.collectionTime } returns 0L
        every { garbageCollectorMXBean.name } returns "G1 Young Generation"
        every { garbageCollectorMXBean.memoryPoolNames } returns emptyArray()

        // Act: Perform the health check
        val health = healthIndicator.health()

        // Assert: Ensure the health is up and there are no warnings
        assertEquals(Status.UP, health.status)
        @Suppress("UNCHECKED_CAST")
        val gcDetails = health.details["G1 Young Generation"] as? Map<String, Any>
        assertNotNull(gcDetails, "Expected details for 'G1 Young Generation', but none were found.")
        assertNull(
            gcDetails?.get("warning"),
            "Expected no warning for 'G1 Young Generation' as there are no collections."
        )
    }
}
