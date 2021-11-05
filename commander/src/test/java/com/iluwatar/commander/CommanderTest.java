package com.iluwatar.commander;

import com.iluwatar.commander.employeehandle.EmployeeDatabase;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.ItemUnavailableException;
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

    @Test
    void testPlaceOrder() throws Exception {
        Commander c = buildCommanderObject();
        var order = new Order(new User("K", "J"), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderWithServiceException() throws Exception {
        Commander c = buildCommanderObjectWithoutDB();
        var order = new Order(new User("K", null), "pen", 1f);
        c.placeOrder(order);
        assertFalse(StringUtils.isBlank(order.id));
    }

    @Test
    void testPlaceOrderWithServiceExceptionAndList() throws Exception {

        List<Exception> l = new ArrayList<Exception>();
        l.add(new ShippingNotPossibleException());
        l.add(new ItemUnavailableException());
        l.add(new IllegalStateException());

        for (Exception e : l) {

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
