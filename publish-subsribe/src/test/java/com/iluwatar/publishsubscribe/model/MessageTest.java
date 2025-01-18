package com.iluwatar.publishsubscribe.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Message model class.
 * 
 * Test coverage:
 * - Message creation with content and topic
 * - String representation (toString)
 * - Getters functionality
 */
public class MessageTest {
    @Test
    void shouldCreateMessageWithContentAndTopic() {
        Message message = new Message("Test content", "test-topic");
        
        assertEquals("Test content", message.getContent());
        assertEquals("test-topic", message.getTopic());
    }

    @Test
    void shouldGenerateCorrectToString() {
        Message message = new Message("Test content", "test-topic");
        String toString = message.toString();
        
        assertTrue(toString.contains("test-topic"));
        assertTrue(toString.contains("Test content"));
    }
}
