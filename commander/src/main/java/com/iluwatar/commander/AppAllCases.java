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
package com.iluwatar.commander;

import com.iluwatar.commander.employeehandle.EmployeeDatabase;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.ItemUnavailableException;
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException;
import com.iluwatar.commander.messagingservice.MessagingDatabase;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.paymentservice.PaymentDatabase;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.queue.QueueDatabase;
import com.iluwatar.commander.shippingservice.ShippingDatabase;
import com.iluwatar.commander.shippingservice.ShippingService;

/**
 * The {@code AppAllCases} class tests various scenarios for the microservices involved
 * in the order placement process. This class consolidates previously separated cases
 * into a single class to manage different success and failure scenarios for each service.
 *
 * <p>The application consists of abstract classes {@link Database} and {@link Service}
 * which are extended by all the databases and services. Each service has a corresponding
 * database to be updated and receives requests from an external user through the
 * {@link Commander} class. There are 5 microservices:
 * <ul>
 *   <li>{@link ShippingService}</li>
 *   <li>{@link PaymentService}</li>
 *   <li>{@link MessagingService}</li>
 *   <li>{@link EmployeeHandle}</li>
 *   <li>{@link QueueDatabase}</li>
 * </ul>
 *
 * <p>Retries are managed using the {@link Retry} class, ensuring idempotence by performing
 * checks before making requests to services and updating the {@link Order} class fields
 * upon request success or definitive failure.
 *
 * <p>This class tests the following scenarios:
 * <ul>
 *   <li>Employee database availability and unavailability</li>
 *   <li>Payment service success and failures</li>
 *   <li>Messaging service database availability and unavailability</li>
 *   <li>Queue database availability and unavailability</li>
 *   <li>Shipping service success and failures</li>
 * </ul>
 *
 * <p>Each scenario is encapsulated in a corresponding method that sets up the service
 * conditions and tests the order placement process.
 *
 * <p>The main method executes all success and failure cases to verify the application's
 * behavior under different conditions.
 *
 * <p><strong>Usage:</strong>
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     AppAllCases app = new AppAllCases();
 *     app.testAllScenarios();
 * }
 * }
 * </pre>
 */

public class AppAllCases {
  private static final RetryParams retryParams = RetryParams.DEFAULT;
  private static final TimeLimits timeLimits = TimeLimits.DEFAULT;

  // Employee Database Fail Case
  void employeeDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var qdb = new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Employee Database Success Case
  void employeeDbSuccessCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Messaging Database Fail Cases
  void messagingDatabaseUnavailableCasePaymentSuccess() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void messagingDatabaseUnavailableCasePaymentError() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }


  void messagingDatabaseUnavailableCasePaymentFailure() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Messaging Database Success Case
  void messagingSuccessCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Payment Database Fail Cases
  void paymentNotPossibleCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new PaymentDetailsErrorException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase(new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void paymentDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Payment Database Success Case
  void paymentSuccessCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase(new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Queue Database Fail Cases
  void queuePaymentTaskDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void queueMessageTaskDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void queueEmployeeDbTaskDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var qdb =
        new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
            new DatabaseUnavailableException(), new DatabaseUnavailableException(),
            new DatabaseUnavailableException(), new DatabaseUnavailableException(),
            new DatabaseUnavailableException(), new DatabaseUnavailableException(),
            new DatabaseUnavailableException(), new DatabaseUnavailableException(),
            new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Queue Database Success Cases
  void queueSuccessCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Shipping Database Fail Cases
  void itemUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void shippingDatabaseUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void shippingItemNotPossibleCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  // Shipping Database Success Cases
  void shippingSuccessCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
    var eh = new EmployeeHandle(new EmployeeDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    var qdb = new QueueDatabase();
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  /**
   * Program entry point.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    AppAllCases app = new AppAllCases();

    // Employee Database cases
    app.employeeDatabaseUnavailableCase();
    app.employeeDbSuccessCase();

    // Messaging Database cases
    app.messagingDatabaseUnavailableCasePaymentSuccess();
    app.messagingDatabaseUnavailableCasePaymentError();
    app.messagingDatabaseUnavailableCasePaymentFailure();
    app.messagingSuccessCase();

    //Payment Database cases
    app.paymentNotPossibleCase();
    app.paymentDatabaseUnavailableCase();
    app.paymentSuccessCase();

    // Queue Database cases
    app.queuePaymentTaskDatabaseUnavailableCase();
    app.queueMessageTaskDatabaseUnavailableCase();
    app.queueEmployeeDbTaskDatabaseUnavailableCase();
    app.queueSuccessCase();

    // Shipping Database cases
    app.itemUnavailableCase();
    app.shippingDatabaseUnavailableCase();
    app.shippingItemNotPossibleCase();
    app.shippingSuccessCase();
  }
}