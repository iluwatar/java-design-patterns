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
// ABOUTME: Health indicator that verifies database transaction capability with retry support.
// ABOUTME: Performs test transactions asynchronously to validate database connectivity.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException
import java.util.function.Supplier

private val logger = KotlinLogging.logger {}

/**
 * A health indicator that checks the health of database transactions by attempting to perform a
 * test transaction using a retry mechanism. If the transaction succeeds after multiple attempts,
 * the health indicator returns [Health.up] and logs a success message. If all retry
 * attempts fail, the health indicator returns [Health.down] and logs an error message.
 */
@Component
open class DatabaseTransactionHealthIndicator(
    /** A repository for performing health checks on the database. */
    private val healthCheckRepository: HealthCheckRepository,
    /** An asynchronous health checker used to execute health checks in a separate thread. */
    private val asynchronousHealthChecker: AsynchronousHealthChecker,
    /** A retry template used to retry the test transaction if it fails due to a transient error. */
    private val retryTemplate: RetryTemplate
) : HealthIndicator {

    /**
     * The timeout in seconds for the health check. If the health check does not complete within this
     * timeout, it will be considered timed out and will return [Health.down].
     */
    @Value("\${health.check.timeout:10}")
    var timeoutInSeconds: Long = 10

    /**
     * Performs a health check by attempting to perform a test transaction with retry support.
     *
     * @return the health status of the database transactions
     */
    override fun health(): Health {
        logger.info { "Calling performCheck with timeout $timeoutInSeconds" }
        val dbTransactionCheck = Supplier<Health> {
            try {
                healthCheckRepository.performTestTransaction()
                Health.up().build()
            } catch (e: Exception) {
                logger.error(e) { "Database transaction health check failed" }
                Health.down(e).build()
            }
        }
        return try {
            asynchronousHealthChecker.performCheck(dbTransactionCheck, timeoutInSeconds).get()
        } catch (e: InterruptedException) {
            logger.error(e) { "Database transaction health check timed out or was interrupted" }
            Thread.currentThread().interrupt()
            Health.down(e).build()
        } catch (e: ExecutionException) {
            logger.error(e) { "Database transaction health check timed out or was interrupted" }
            Thread.currentThread().interrupt()
            Health.down(e).build()
        }
    }
}
