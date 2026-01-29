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
// ABOUTME: Health indicator that monitors JVM heap memory usage with configurable thresholds.
// ABOUTME: Uses asynchronous health checking to avoid blocking and reports memory consumption.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.util.concurrent.ExecutionException
import java.util.function.Supplier

private val logger = KotlinLogging.logger {}

/**
 * A custom health indicator that checks the memory usage of the application and reports the health
 * status accordingly. It uses an asynchronous health checker to perform the health check and a
 * configurable memory usage threshold to determine the health status.
 */
@Component
open class MemoryHealthIndicator(
    private val asynchronousHealthChecker: AsynchronousHealthChecker
) : HealthIndicator {

    /** The timeout in seconds for the health check. */
    @Value("\${health.check.timeout:10}")
    private var timeoutInSeconds: Long = 10

    /**
     * The memory usage threshold in percentage. If the memory usage is less than this threshold, the
     * health status is reported as UP. Otherwise, the health status is reported as DOWN.
     */
    @Value("\${health.check.memory.threshold:0.9}")
    private var memoryThreshold: Double = 0.9

    /**
     * Performs a health check by checking the memory usage of the application.
     *
     * @return the health status of the application
     */
    fun checkMemory(): Health {
        val memoryCheck = Supplier<Health> {
            val memoryMxBean = ManagementFactory.getMemoryMXBean()
            val heapMemoryUsage = memoryMxBean.heapMemoryUsage
            val maxMemory = heapMemoryUsage.max
            val usedMemory = heapMemoryUsage.used

            val memoryUsage = usedMemory.toDouble() / maxMemory.toDouble()
            val format = String.format("%.2f%% of %d max", memoryUsage * 100, maxMemory)

            if (memoryUsage < memoryThreshold) {
                logger.info { "Memory usage is below threshold: $format" }
                Health.up().withDetail("memory usage", format).build()
            } else {
                Health.down().withDetail("memory usage", format).build()
            }
        }

        return try {
            val future = asynchronousHealthChecker.performCheck(memoryCheck, timeoutInSeconds)
            future.get()
        } catch (e: InterruptedException) {
            logger.error(e) { "Health check interrupted" }
            Thread.currentThread().interrupt()
            Health.down().withDetail("error", "Health check interrupted").build()
        } catch (e: ExecutionException) {
            logger.error(e) { "Health check failed" }
            val cause = e.cause ?: e
            Health.down().withDetail("error", cause.toString()).build()
        }
    }

    /**
     * Retrieves the health status of the application by checking the memory usage.
     *
     * @return the health status of the application
     */
    override fun health(): Health {
        return checkMemory()
    }
}
