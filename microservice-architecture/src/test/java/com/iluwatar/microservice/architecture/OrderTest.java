package com.iluwatar.microservice.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    void testCreate() {
        String response = order.create();
        assertTrue(response.contains("Order Created with ID: 1"));
    }

    @Test
    void testHistory() {
        // Assuming history() returns a String representation of all orders
        order.create(); // Create an order to ensure history is not empty
        String history = order.history();
        assertTrue(history.contains("Sample Order Details for ID: 1"));
    }

    @Test
    void testModify() {
        order.create(); // Create an order to modify
        String modified = order.modify(1L, "Updated Order Details");
        assertEquals("Modified order with ID: 1", modified);

        String notFound = order.modify(999L, "Some Details");
        assertEquals("Order not found", notFound);
    }

    @Test
    void testDelete() {
        order.create(); // Create an order to delete
        String deleted = order.delete(1L);
        assertEquals("Deleted order with ID: 1", deleted);

        String notFound = order.delete(999L);
        assertEquals("Order not found", notFound);
    }
}
