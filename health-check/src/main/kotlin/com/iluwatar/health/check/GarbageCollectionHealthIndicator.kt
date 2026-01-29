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
// ABOUTME: Health indicator that monitors JVM garbage collection status and memory pools.
// ABOUTME: Reports collection counts, times, and memory usage warnings for each garbage collector.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.lang.management.GarbageCollectorMXBean
import java.lang.management.ManagementFactory
import java.lang.management.MemoryPoolMXBean

private val logger = KotlinLogging.logger {}

/**
 * A custom health indicator that checks the garbage collection status of the application and
 * reports the health status accordingly. It gathers information about the collection count,
 * collection time, memory pool name, and garbage collector algorithm for each garbage collector and
 * presents the details in a structured manner.
 */
@Component
open class GarbageCollectionHealthIndicator : HealthIndicator {

    /**
     * The memory usage threshold above which a warning message is included in the health check
     * report.
     */
    @Value("\${memory.usage.threshold:0.8}")
    var memoryUsageThreshold: Double = 0.8

    /**
     * Performs a health check by gathering garbage collection metrics and evaluating the overall
     * health of the garbage collection system.
     *
     * @return a [Health] object representing the health status of the garbage collection system
     */
    override fun health(): Health {
        val gcBeans = getGarbageCollectorMxBeans()
        val memoryPoolMxBeans = getMemoryPoolMxBeans()
        val gcDetails = mutableMapOf<String, Map<String, String>>()

        for (gcBean in gcBeans) {
            val collectorDetails = createCollectorDetails(gcBean, memoryPoolMxBeans)
            gcDetails[gcBean.name] = collectorDetails
        }

        return Health.up().withDetails(gcDetails).build()
    }

    /**
     * Creates details for the given garbage collector, including collection count, collection time,
     * and memory pool information.
     *
     * @param gcBean The garbage collector MXBean
     * @param memoryPoolMxBeans List of memory pool MXBeans
     * @return Map containing details for the garbage collector
     */
    private fun createCollectorDetails(
        gcBean: GarbageCollectorMXBean,
        memoryPoolMxBeans: List<MemoryPoolMXBean>
    ): Map<String, String> {
        val collectorDetails = mutableMapOf<String, String>()
        val count = gcBean.collectionCount
        val time = gcBean.collectionTime
        collectorDetails["count"] = String.format("%d", count)
        collectorDetails["time"] = String.format("%dms", time)

        val memoryPoolNames = gcBean.memoryPoolNames
        val memoryPoolNamesList = memoryPoolNames.toList()
        if (memoryPoolNamesList.isNotEmpty()) {
            addMemoryPoolDetails(collectorDetails, memoryPoolMxBeans, memoryPoolNamesList)
        } else {
            logger.error { "Garbage collector '${gcBean.name}' does not have any memory pools" }
        }

        return collectorDetails
    }

    /**
     * Adds memory pool details to the collector details.
     *
     * @param collectorDetails Map containing details for the garbage collector
     * @param memoryPoolMxBeans List of memory pool MXBeans
     * @param memoryPoolNamesList List of memory pool names associated with the garbage collector
     */
    private fun addMemoryPoolDetails(
        collectorDetails: MutableMap<String, String>,
        memoryPoolMxBeans: List<MemoryPoolMXBean>,
        memoryPoolNamesList: List<String>
    ) {
        for (memoryPoolMxBean in memoryPoolMxBeans) {
            if (memoryPoolNamesList.contains(memoryPoolMxBean.name)) {
                val memoryUsage = memoryPoolMxBean.usage.used.toDouble() / memoryPoolMxBean.usage.max.toDouble()
                if (memoryUsage > memoryUsageThreshold) {
                    collectorDetails["warning"] = String.format(
                        "Memory pool '%s' usage is high (%2f%%)",
                        memoryPoolMxBean.name, memoryUsage
                    )
                }

                collectorDetails["memoryPools"] = String.format(
                    "%s: %s%%",
                    memoryPoolMxBean.name, memoryUsage
                )
            }
        }
    }

    /**
     * Retrieves the list of garbage collector MXBeans using ManagementFactory.
     *
     * @return a list of [GarbageCollectorMXBean] objects representing the garbage collectors
     */
    internal open fun getGarbageCollectorMxBeans(): List<GarbageCollectorMXBean> {
        return ManagementFactory.getGarbageCollectorMXBeans()
    }

    /**
     * Retrieves the list of memory pool MXBeans using ManagementFactory.
     *
     * @return a list of [MemoryPoolMXBean] objects representing the memory pools
     */
    internal open fun getMemoryPoolMxBeans(): List<MemoryPoolMXBean> {
        return ManagementFactory.getMemoryPoolMXBeans()
    }
}
