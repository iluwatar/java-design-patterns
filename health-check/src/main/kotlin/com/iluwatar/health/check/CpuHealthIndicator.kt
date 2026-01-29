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
// ABOUTME: Health indicator that monitors system CPU usage and load averages.
// ABOUTME: Reports health status based on configurable CPU load thresholds.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.lang.management.OperatingSystemMXBean
import java.time.Instant

private val logger = KotlinLogging.logger {}

/** A health indicator that checks the health of the system's CPU. */
@Component
open class CpuHealthIndicator : HealthIndicator {

    /** The operating system MXBean used to gather CPU health information. */
    var osBean: OperatingSystemMXBean? = null

    /** Initializes the [OperatingSystemMXBean] instance. */
    @PostConstruct
    fun init() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean()
    }

    /**
     * The system CPU load threshold. If the system CPU load is above this threshold, the health
     * indicator will return a `down` health status.
     */
    @Value("\${cpu.system.load.threshold:80.0}")
    var systemCpuLoadThreshold: Double = 80.0

    /**
     * The process CPU load threshold. If the process CPU load is above this threshold, the health
     * indicator will return a `down` health status.
     */
    @Value("\${cpu.process.load.threshold:50.0}")
    var processCpuLoadThreshold: Double = 50.0

    /**
     * The load average threshold. If the load average is above this threshold, the health indicator
     * will return an `up` health status with a warning message.
     */
    @Value("\${cpu.load.average.threshold:0.75}")
    var loadAverageThreshold: Double = 0.75

    companion object {
        private const val ERROR_MESSAGE = "error"
        private const val HIGH_SYSTEM_CPU_LOAD_MESSAGE = "High system CPU load: {}"
        private const val HIGH_PROCESS_CPU_LOAD_MESSAGE = "High process CPU load: {}"
        private const val HIGH_LOAD_AVERAGE_MESSAGE = "High load average: {}"
        private const val HIGH_PROCESS_CPU_LOAD_MESSAGE_WITHOUT_PARAM = "High process CPU load"
        private const val HIGH_SYSTEM_CPU_LOAD_MESSAGE_WITHOUT_PARAM = "High system CPU load"
        private const val HIGH_LOAD_AVERAGE_MESSAGE_WITHOUT_PARAM = "High load average"
    }

    /**
     * Checks the health of the system's CPU and returns a health indicator object.
     *
     * @return a health indicator object
     */
    override fun health(): Health {
        val currentOsBean = osBean
        if (currentOsBean !is com.sun.management.OperatingSystemMXBean) {
            logger.error { "Unsupported operating system MXBean: ${currentOsBean?.javaClass?.name}" }
            return Health.unknown()
                .withDetail(ERROR_MESSAGE, "Unsupported operating system MXBean")
                .build()
        }

        val sunOsBean = currentOsBean as com.sun.management.OperatingSystemMXBean
        val systemCpuLoad = sunOsBean.cpuLoad * 100
        val processCpuLoad = sunOsBean.processCpuLoad * 100
        val availableProcessors = sunOsBean.availableProcessors
        val loadAverage = sunOsBean.systemLoadAverage

        val details = mutableMapOf<String, Any>(
            "timestamp" to Instant.now(),
            "systemCpuLoad" to String.format("%.2f%%", systemCpuLoad),
            "processCpuLoad" to String.format("%.2f%%", processCpuLoad),
            "availableProcessors" to availableProcessors,
            "loadAverage" to loadAverage
        )

        return when {
            systemCpuLoad > systemCpuLoadThreshold -> {
                logger.error { HIGH_SYSTEM_CPU_LOAD_MESSAGE.replace("{}", systemCpuLoad.toString()) }
                Health.down()
                    .withDetails(details)
                    .withDetail(ERROR_MESSAGE, HIGH_SYSTEM_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
                    .build()
            }
            processCpuLoad > processCpuLoadThreshold -> {
                logger.error { HIGH_PROCESS_CPU_LOAD_MESSAGE.replace("{}", processCpuLoad.toString()) }
                Health.down()
                    .withDetails(details)
                    .withDetail(ERROR_MESSAGE, HIGH_PROCESS_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
                    .build()
            }
            loadAverage > (availableProcessors * loadAverageThreshold) -> {
                logger.error { HIGH_LOAD_AVERAGE_MESSAGE.replace("{}", loadAverage.toString()) }
                Health.up()
                    .withDetails(details)
                    .withDetail(ERROR_MESSAGE, HIGH_LOAD_AVERAGE_MESSAGE_WITHOUT_PARAM)
                    .build()
            }
            else -> {
                Health.up().withDetails(details).build()
            }
        }
    }
}
