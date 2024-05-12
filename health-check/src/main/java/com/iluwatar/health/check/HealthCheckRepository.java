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
package com.iluwatar.health.check;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * A repository class for managing health check records in the database. This class provides methods
 * for checking the health of the database connection and performing test transactions.
 *
 * @author ydoksanbir
 */
@Slf4j
@Repository
public class HealthCheckRepository {

  private static final String HEALTH_CHECK_OK = "OK";

  @PersistenceContext private EntityManager entityManager;

  /**
   * Checks the health of the database connection by executing a simple query that should always
   * return 1 if the connection is healthy.
   *
   * @return 1 if the database connection is healthy, or null otherwise
   */
  public Integer checkHealth() {
    try {
      return (Integer) entityManager.createNativeQuery("SELECT 1").getSingleResult();
    } catch (Exception e) {
      LOGGER.error("Health check query failed", e);
      throw e;
    }
  }

  /**
   * Performs a test transaction by writing a record to the `health_check` table, reading it back,
   * and then deleting it. If any of these operations fail, an exception is thrown.
   *
   * @throws Exception if the test transaction fails
   */
  @Transactional
  public void performTestTransaction() throws Exception {
    try {
      HealthCheck healthCheck = new HealthCheck();
      healthCheck.setStatus(HEALTH_CHECK_OK);
      entityManager.persist(healthCheck);
      entityManager.flush();
      HealthCheck retrievedHealthCheck = entityManager.find(HealthCheck.class, healthCheck.getId());
      entityManager.remove(retrievedHealthCheck);
    } catch (Exception e) {
      LOGGER.error("Test transaction failed", e);
      throw e;
    }
  }
}
