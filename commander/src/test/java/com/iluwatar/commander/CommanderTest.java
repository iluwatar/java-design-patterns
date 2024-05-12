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
import com.iluwatar.commander.exceptions.ShippingNotPossibleException;
import com.iluwatar.commander.messagingservice.MessagingDatabase;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.paymentservice.PaymentDatabase;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.queue.QueueDatabase;
import com.iluwatar.commander.shippingservice.ShippingDatabase;
import com.iluwatar.commander.shippingservice.ShippingService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CommanderTest {

    private final RetryParams retryParams = new RetryParams(1, 1_000L);

    private final TimeLimits timeLimits = new TimeLimits(1L, 1000L, 6000L, 5000L, 2000L);

    private static final List<Exception> exceptionList = new ArrayList<>();

    static {
        exceptionList.add(new DatabaseUnavailableException());
        exceptionList.add(new ShippingNotPossibleException());
        exceptionList.add(new ItemUnavailableException());
        exceptionList.add(new PaymentDetailsErrorException());
        exceptionList.add(new IllegalStateException());
    }

    private Commander buildCommanderObject() {
        return buildCommanderObject(false);
    }

    private Commander buildCommanderObject(boolean nonPaymentException) {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException());

        ShippingService shippingService;
        MessagingService messagingService;
        if (nonPaymentException) {
            shippingService = new ShippingService(new ShippingDatabase(), new DatabaseUnavailableException());
            messagingService = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());

        } else {
            shippingService = new ShippingService(new ShippingDatabase(), new DatabaseUnavailableException());
            messagingService = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());

        }
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException());
        return new Commander(employeeHandle, paymentService, shippingService,
                messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectVanilla() {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException());
        var shippingService = new ShippingService(new ShippingDatabase());
        var messagingService = new MessagingService(new MessagingDatabase());
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                        new DatabaseUnavailableException(), new DatabaseUnavailableException());
        return new Commander(employeeHandle, paymentService, shippingService,
          messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectUnknownException() {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase(), new IllegalStateException());
        var shippingService = new ShippingService(new ShippingDatabase());
        var messagingService = new MessagingService(new MessagingDatabase());
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new IllegalStateException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new IllegalStateException());
        return new Commander(employeeHandle, paymentService, shippingService,
            messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectNoPaymentException1() {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase());
        var shippingService = new ShippingService(new ShippingDatabase());
        var messagingService = new MessagingService(new MessagingDatabase());
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new IllegalStateException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new IllegalStateException());
        return new Commander(employeeHandle, paymentService, shippingService,
            messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectNoPaymentException2() {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase());
        var shippingService = new ShippingService(new ShippingDatabase());
        var messagingService = new MessagingService(new MessagingDatabase(), new IllegalStateException());
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new IllegalStateException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new IllegalStateException());
        return new Commander(employeeHandle, paymentService, shippingService,
            messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectNoPaymentException3() {
        PaymentService paymentService = new PaymentService
                (new PaymentDatabase());
        var shippingService = new ShippingService(new ShippingDatabase());
        var messagingService = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
        var employeeHandle = new EmployeeHandle
                (new EmployeeDatabase(), new IllegalStateException());
        var qdb = new QueueDatabase
                (new DatabaseUnavailableException(), new IllegalStateException());
        return new Commander(employeeHandle, paymentService, shippingService,
            messagingService, qdb, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectWithDB() {
        return buildCommanderObjectWithoutDB(false, false, new IllegalStateException());
    }

    private Commander buildCommanderObjectWithDB(boolean includeException, boolean includeDBException, Exception e) {
        var l = includeDBException ? new DatabaseUnavailableException() : e;
        PaymentService paymentService;
        ShippingService shippingService;
        MessagingService messagingService;
        EmployeeHandle employeeHandle;
        if (includeException) {
            paymentService = new PaymentService
                    (new PaymentDatabase(), l);
            shippingService = new ShippingService(new ShippingDatabase(), l);
            messagingService = new MessagingService(new MessagingDatabase(), l);
            employeeHandle = new EmployeeHandle
                    (new EmployeeDatabase(), l);
        } else {
            paymentService = new PaymentService
                    (null);
            shippingService = new ShippingService(null);
            messagingService = new MessagingService(null);
            employeeHandle = new EmployeeHandle
                    (null);
        }


        return new Commander(employeeHandle, paymentService, shippingService,
                messagingService, null, retryParams, timeLimits);
    }

    private Commander buildCommanderObjectWithoutDB() {
        return buildCommanderObjectWithoutDB(false, false, new IllegalStateException());
    }

    private Commander buildCommanderObjectWithoutDB(boolean includeException, boolean includeDBException, Exception e) {
        var l = includeDBException ? new DatabaseUnavailableException() : e;
        PaymentService paymentService;
        ShippingService shippingService;
        MessagingService messagingService;
        EmployeeHandle employeeHandle;
        if (includeException) {
            paymentService = new PaymentService
                    (null, l);
            shippingService = new ShippingService(null, l);
            messagingService = new MessagingService(null, l);
            employeeHandle = new EmployeeHandle
                    (null, l);
        } else {
            paymentService = new PaymentService
                    (null);
            shippingService = new ShippingService(null);
            messagingService = new MessagingService(null);
            employeeHandle = new EmployeeHandle
                    (null);
        }


        return new Commander(employeeHandle, paymentService, shippingService,
                messagingService, null, retryParams, timeLimits);
    }

    @Test
    void testPlaceOrderVanilla() {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObjectVanilla();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrder() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObject(true);
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrder2() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObject(false);
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoException1() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObjectNoPaymentException1();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoException2() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObjectNoPaymentException2();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoException3() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObjectNoPaymentException3();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoException4() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            Commander c = buildCommanderObjectNoPaymentException3();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                c.placeOrder(order);
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderUnknownException() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObjectUnknownException();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderShortDuration() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObject(true);
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderShortDuration2() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObject(false);
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoExceptionShortMsgDuration() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObjectNoPaymentException1();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderNoExceptionShortQueueDuration() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObjectUnknownException();
            var order = new Order(new User("K", "J"), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderWithDatabase() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObjectWithDB();
            var order = new Order(new User("K", null), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderWithDatabaseAndExceptions() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;

            for (Exception e : exceptionList) {

                Commander c = buildCommanderObjectWithDB(true, true, e);
                var order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithDB(true, false, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithDB(false, false, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithDB(false, true, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }
            }
        }
    }

    @Test
    void testPlaceOrderWithoutDatabase() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;
            Commander c = buildCommanderObjectWithoutDB();
            var order = new Order(new User("K", null), "pen", 1f);
            for (Order.MessageSent ms : Order.MessageSent.values()) {
                c.placeOrder(order);
                assertFalse(StringUtils.isBlank(order.id));
            }
        }
    }

    @Test
    void testPlaceOrderWithoutDatabaseAndExceptions() throws Exception {
      long paymentTime = timeLimits.paymentTime();
      long queueTaskTime = timeLimits.queueTaskTime();
      long messageTime = timeLimits.messageTime();
      long employeeTime = timeLimits.employeeTime();
      long queueTime = timeLimits.queueTime();
        for (double d = 0.1; d < 2; d = d + 0.1) {
            paymentTime *= d;
            queueTaskTime *= d;
            messageTime *= d;
            employeeTime *= d;
            queueTime *= d;

            for (Exception e : exceptionList) {

                Commander c = buildCommanderObjectWithoutDB(true, true, e);
                var order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithoutDB(true, false, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithoutDB(false, false, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }

                c = buildCommanderObjectWithoutDB(false, true, e);
                order = new Order(new User("K", null), "pen", 1f);
                for (Order.MessageSent ms : Order.MessageSent.values()) {
                    c.placeOrder(order);
                    assertFalse(StringUtils.isBlank(order.id));
                }
            }
        }
    }

}