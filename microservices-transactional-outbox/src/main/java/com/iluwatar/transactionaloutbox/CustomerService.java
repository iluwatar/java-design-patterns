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

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerService {

  private final EntityManager entityManager;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public CustomerService(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.outboxRepository = new OutboxRepository(entityManager);
  }

  public void createCustomer(String username) throws Exception {
    entityManager.getTransaction().begin();
    try {
      var customer = new Customer(username);
      entityManager.persist(customer);

      String payload = objectMapper.writeValueAsString(customer);
      var event = new OutboxEvent("CUSTOMER_CREATED", payload);
      outboxRepository.save(event);

      entityManager.getTransaction().commit();
      LOGGER.info("SUCCESS: Customer and OutboxEvent saved transactionally.");

    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      LOGGER.error("ERROR: Transaction rolled back.");
      throw e;
    }
  }
}
