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
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommanderTest {

    private final int numOfRetries = 1;
    private final long retryDuration = 1_000;
    private final long queueTime = 1_00;
    private final long queueTaskTime = 1_000;
    private final long paymentTime = 6_000;
    private final long messageTime = 5_000;
    private final long employeeTime = 2_000;

    private static List<Exception> exceptionList = new ArrayList<>();

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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, null, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
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
                messagingService, null, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            //no-op
        }
    }

    @Test
    void testPlaceOrder() throws Exception {
        Commander c = buildCommanderObject(true);
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrder2() throws Exception {
        Commander c = buildCommanderObject(false);
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoException1() throws Exception {
        Commander c = buildCommanderObjectNoPaymentException1();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoException2() throws Exception {
        Commander c = buildCommanderObjectNoPaymentException2();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoException3() throws Exception {
        Commander c = buildCommanderObjectNoPaymentException3();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoException4() throws Exception {
        Commander c = buildCommanderObjectNoPaymentException3();
        var order = new Order(new User("K", "J"), "pen", 1f);
        sleep(queueTaskTime / 10);
        c.placeOrder(order);
        c.placeOrder(order);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderUnknownException() throws Exception {
        Commander c = buildCommanderObjectUnknownException();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderShortDuration() throws Exception {
        Commander c = buildCommanderObject(true);
        var order = new Order(new User("K", "J"), "pen", 1f);
        sleep(paymentTime);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderShortDuration2() throws Exception {
        Commander c = buildCommanderObject(false);
        var order = new Order(new User("K", "J"), "pen", 1f);
        sleep(paymentTime);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoExceptionShortMsgDuration() throws Exception {
        Commander c = buildCommanderObjectNoPaymentException1();
        var order = new Order(new User("K", "J"), "pen", 1f);
        sleep(messageTime);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderNoExceptionShortQueueDuration() throws Exception {
        Commander c = buildCommanderObjectUnknownException();
        var order = new Order(new User("K", "J"), "pen", 1f);
        sleep(queueTime);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderWithDatabase() throws Exception {
        Commander c = buildCommanderObjectWithDB();
        var order = new Order(new User("K", null), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderWithDatabaseAndExceptions() throws Exception {

        for (Exception e : exceptionList) {

            Commander c = buildCommanderObjectWithDB(true, true, e);
            var order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithDB(true, false, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithDB(false, false, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithDB(false, true, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));
        }
    }

    @Test
    void testPlaceOrderWithoutDatabase() throws Exception {
        Commander c = buildCommanderObjectWithoutDB();
        var order = new Order(new User("K", null), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderWithoutDatabaseAndExceptions() throws Exception {

        for (Exception e : exceptionList) {

            Commander c = buildCommanderObjectWithoutDB(true, true, e);
            var order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithoutDB(true, false, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithoutDB(false, false, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));

            c = buildCommanderObjectWithoutDB(false, true, e);
            order = new Order(new User("K", null), "pen", 1f);
            c.placeOrder(order);
            assertFalse(StringUtils.isBlank(order.id));
        }
    }

}
