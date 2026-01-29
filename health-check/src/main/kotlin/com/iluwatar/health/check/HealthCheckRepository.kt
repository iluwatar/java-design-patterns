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
// ABOUTME: Repository for managing health check records and verifying database connectivity.
// ABOUTME: Provides methods for connection health checks and test transaction execution.
package com.iluwatar.health.check

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

private val logger = KotlinLogging.logger {}

/**
 * A repository class for managing health check records in the database. This class provides methods
 * for checking the health of the database connection and performing test transactions.
 */
@Repository
open class HealthCheckRepository {

    companion object {
        private const val HEALTH_CHECK_OK = "OK"
    }

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    /**
     * Checks the health of the database connection by executing a simple query that should always
     * return 1 if the connection is healthy.
     *
     * @return 1 if the database connection is healthy, or null otherwise
     */
    fun checkHealth(): Int? {
        return try {
            entityManager.createNativeQuery("SELECT 1").singleResult as Int
        } catch (e: Exception) {
            logger.error(e) { "Health check query failed" }
            throw e
        }
    }

    /**
     * Performs a test transaction by writing a record to the `health_check` table, reading it back,
     * and then deleting it. If any of these operations fail, an exception is thrown.
     *
     * @throws Exception if the test transaction fails
     */
    @Transactional
    @Throws(Exception::class)
    open fun performTestTransaction() {
        try {
            val healthCheck = HealthCheck(status = HEALTH_CHECK_OK)
            entityManager.persist(healthCheck)
            entityManager.flush()
            val retrievedHealthCheck = entityManager.find(HealthCheck::class.java, healthCheck.id)
            entityManager.remove(retrievedHealthCheck)
        } catch (e: Exception) {
            logger.error(e) { "Test transaction failed" }
            throw e
        }
    }
}
