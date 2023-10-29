package com.iluwatar.microservice.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest {
    private Consumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new Consumer();
    }

    @Test
    void testCreate() {
        String response = consumer.create();
        assertTrue(response.contains("Consumer Created with ID: 1"));
    }

    @Test
    void testFind() {
        consumer.create(); // Create a consumer to test find
        String found = consumer.find(1L);
        assertTrue(found.contains("Sample Consumer Details for ID: 1"));

        String notFound = consumer.find(999L);
        assertEquals("Consumer not found", notFound);
    }

    @Test
    void testModify() {
        consumer.create(); // Create a consumer to modify
        String modified = consumer.modify(1L, "New Details");
        assertEquals("Modified consumer with ID: 1", modified);

        String notFound = consumer.modify(999L, "Details");
        assertEquals("Consumer not found", notFound);
    }

    @Test
    void testDelete() {
        consumer.create(); // Create a consumer to delete
        String deleted = consumer.delete(1L);
        assertEquals("Deleted consumer with ID: 1", deleted);

        String notFound = consumer.delete(999L);
        assertEquals("Consumer not found", notFound);
    }
}

