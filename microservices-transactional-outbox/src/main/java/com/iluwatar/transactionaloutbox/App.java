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

import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) throws Exception {
    var entityManagerFactory = Persistence.createEntityManagerFactory("transactional-outbox-pu");
    var entityManager = entityManagerFactory.createEntityManager();

    var customerService = new CustomerService(entityManager);
    var messageBroker = new MessageBroker();
    var eventPoller = new EventPoller(entityManager, messageBroker);

    eventPoller.start();

    LOGGER.info("Running simulation...");
    Thread.sleep(1000);

    customerService.createCustomer("john.doe");
    Thread.sleep(5000);

    customerService.createCustomer("jane.doe");
    Thread.sleep(5000);

    eventPoller.stop();
    entityManager.close();
    entityManagerFactory.close();
    LOGGER.info("Simulation finished.");
  }
}
