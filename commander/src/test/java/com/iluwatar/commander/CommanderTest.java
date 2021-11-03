package com.iluwatar.commander;

import com.iluwatar.commander.employeehandle.EmployeeDatabase;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.messagingservice.MessagingDatabase;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.paymentservice.PaymentDatabase;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.queue.QueueDatabase;
import com.iluwatar.commander.shippingservice.ShippingDatabase;
import com.iluwatar.commander.shippingservice.ShippingService;

import org.junit.jupiter.api.Test;

class CommanderTest {

    private final int numOfRetries = 1;
    private final long retryDuration = 10_000;
    private final long queueTime = 1_000;
    private final long queueTaskTime = 10_000;
    private final long paymentTime = 60_000;
    private final long messageTime = 50_000;
    private final long employeeTime = 20_000;

    private Commander buildCommanderObject() {
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
                messagingService, qdb, numOfRetries, retryDuration,
                queueTime, queueTaskTime, paymentTime, messageTime, employeeTime);
    }

    private Commander buildCommanderObjectWithoutPaymentDB() {
        return buildCommanderObjectWithoutPaymentDB(false);
    }

    private Commander buildCommanderObjectWithoutPaymentDB(boolean includeException) {
        var l = new DatabaseUnavailableException();
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

    @Test
    void testPlaceOrder() throws Exception {
        Commander c = buildCommanderObject();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
    }

    @Test
    void testPlaceOrderWithServiceException() throws Exception {
        Commander c = buildCommanderObjectWithoutPaymentDB();
        var order = new Order(new User("K", null), "pen", 1f);
        c.placeOrder(order);
    }

    @Test
    void testPlaceOrderWithServiceExceptionAndList() throws Exception {
        Commander c = buildCommanderObjectWithoutPaymentDB(true);
        var order = new Order(new User("K", null), "pen", 1f);
        c.placeOrder(order);
    }
}
