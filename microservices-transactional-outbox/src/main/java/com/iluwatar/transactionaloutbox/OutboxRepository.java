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
package com.iluwatar.transactionaloutbox;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;

public class OutboxRepository {

  private final EntityManager entityManager;

  public OutboxRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void save(OutboxEvent event) {
    entityManager.persist(event);
  }

  public void markAsProcessed(OutboxEvent event) {
    event.setProcessed(true);
    entityManager.merge(event);
  }

  private static final int BATCH_SIZE = 100;

  public List<OutboxEvent> findUnprocessedEvents() {
    return entityManager
        .createQuery(
            "SELECT e FROM OutboxEvent e WHERE e.processed = false ORDER BY e.sequenceNumber",
            OutboxEvent.class)
        .setMaxResults(BATCH_SIZE)
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .getResultList();
  }
}
