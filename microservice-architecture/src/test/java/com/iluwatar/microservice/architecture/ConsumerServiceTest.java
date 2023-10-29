package com.iluwatar.microservice.architecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceTest {
    @Mock
    private Consumer consumer;

    @InjectMocks
    private ConsumeService consumeService;

    @BeforeEach
    void setUp() {
        // Setup can be used for additional initializations if needed.
    }

    @Test
    void testCreateConsumer() {
        // Define the behavior of the mock
        when(consumer.create()).thenReturn("Consumer Created with ID: 1");

        // Call the method under test
        String result = consumeService.createConsumer();

        // Validate the result
        assertEquals("Consumer Created with ID: 1", result);
    }

    @Test
    void testFindConsumer() {
        when(consumer.find(1L)).thenReturn("Consumer Details for ID: 1");
        String result = consumeService.findConsumer(1L);
        assertEquals("Consumer Details for ID: 1", result);
    }

    @Test
    void testModifyConsumer() {
        when(consumer.modify(1L, "New Consumer Details")).thenReturn("Modified consumer with ID: 1");
        String result = consumeService.modifyConsumer(1L, "New Consumer Details");
        assertEquals("Modified consumer with ID: 1", result);
    }

    @Test
    void testDeleteConsumer() {
        when(consumer.delete(1L)).thenReturn("Deleted consumer with ID: 1");
        String result = consumeService.deleteConsumer(1L);
        assertEquals("Deleted consumer with ID: 1", result);
    }
}
