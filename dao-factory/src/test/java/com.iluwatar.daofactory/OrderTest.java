package com.iluwatar.daofactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    private final String senderName = "Josh";
    private final String receiverName = "Zack";
    private final String dest = "Paris";

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setSenderName(senderName);
        order.setReceiverName(receiverName);
        order.setDestination(dest);
    }

    @Test
    void testGetAndSetFirstName() {
        String newSender = "Richard";
        order.setSenderName(newSender);
        assertEquals(newSender, order.getSenderName());
    }

    @Test
    void testGetAndSetLastName() {
        String newReceiver = "Reynaldo";
        order.setReceiverName(newReceiver);
        assertEquals(newReceiver, order.getReceiverName());
    }

    @Test
    void testGetAndSetLocation() {
        String newDestination = "Boston";
        order.setDestination(newDestination);
        assertEquals(newDestination, order.getDestination());
    }

    @Test
    void testEqualsWithSameObjects() {
        assertEquals(order, order);
        assertEquals(order.hashCode(), order.hashCode());
    }
}