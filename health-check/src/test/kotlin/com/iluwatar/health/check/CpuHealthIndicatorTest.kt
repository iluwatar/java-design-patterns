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
// ABOUTME: Test class for CpuHealthIndicator component.
// ABOUTME: Verifies health status reporting based on CPU load thresholds.
package com.iluwatar.health.check

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.actuate.health.Status

/** Test class for the [CpuHealthIndicator] class. */
class CpuHealthIndicatorTest {

    /** The CPU health indicator to be tested. */
    private lateinit var cpuHealthIndicator: CpuHealthIndicator

    /** The mocked operating system MXBean used to simulate CPU health information. */
    private lateinit var mockOsBean: com.sun.management.OperatingSystemMXBean

    /**
     * Sets up the test environment before each test method.
     *
     * Mocks the [com.sun.management.OperatingSystemMXBean] and sets it in the [CpuHealthIndicator]
     * instance.
     */
    @BeforeEach
    fun setUp() {
        // Mock the com.sun.management.OperatingSystemMXBean using Mockito
        mockOsBean = mock(com.sun.management.OperatingSystemMXBean::class.java)
        cpuHealthIndicator = CpuHealthIndicator()
        cpuHealthIndicator.osBean = mockOsBean
    }

    /**
     * Tests that the health status is DOWN when the system CPU load is high.
     *
     * Sets the system CPU load to 90% and mocks the other getters to return appropriate values.
     * Executes the health check and asserts that the health status is DOWN and the error message
     * indicates high system CPU load.
     */
    @Test
    fun whenSystemCpuLoadIsHigh_thenHealthIsDown() {
        // Set thresholds for testing within the test method to avoid issues with Spring's @Value
        cpuHealthIndicator.systemCpuLoadThreshold = 80.0
        cpuHealthIndicator.processCpuLoadThreshold = 50.0
        cpuHealthIndicator.loadAverageThreshold = 0.75

        // Mock the getters to return your desired values
        `when`(mockOsBean.cpuLoad).thenReturn(0.9) // Simulate 90% system CPU load
        `when`(mockOsBean.availableProcessors).thenReturn(8)
        `when`(mockOsBean.systemLoadAverage).thenReturn(9.0)

        // Execute the health check
        val health = cpuHealthIndicator.health()

        // Assertions
        assertEquals(
            Status.DOWN,
            health.status,
            "Health status should be DOWN when system CPU load is high"
        )
        assertEquals(
            "High system CPU load",
            health.details["error"],
            "Error message should indicate high system CPU load"
        )
    }

    /**
     * Tests that the health status is DOWN when the process CPU load is high.
     *
     * Sets the process CPU load to 80% and mocks the other getters to return appropriate values.
     * Executes the health check and asserts that the health status is DOWN and the error message
     * indicates high process CPU load.
     */
    @Test
    fun whenProcessCpuLoadIsHigh_thenHealthIsDown() {
        // Set thresholds for testing within the test method to avoid issues with Spring's @Value
        cpuHealthIndicator.systemCpuLoadThreshold = 80.0
        cpuHealthIndicator.processCpuLoadThreshold = 50.0
        cpuHealthIndicator.loadAverageThreshold = 0.75

        // Mock the getters to return your desired values
        `when`(mockOsBean.cpuLoad).thenReturn(0.5) // Simulate 50% system CPU load
        `when`(mockOsBean.processCpuLoad).thenReturn(0.8) // Simulate 80% process CPU load
        `when`(mockOsBean.availableProcessors).thenReturn(8)
        `when`(mockOsBean.systemLoadAverage).thenReturn(5.0)

        // Execute the health check
        val health = cpuHealthIndicator.health()

        // Assertions
        assertEquals(
            Status.DOWN,
            health.status,
            "Health status should be DOWN when process CPU load is high"
        )
        assertEquals(
            "High process CPU load",
            health.details["error"],
            "Error message should indicate high process CPU load"
        )
    }
}
