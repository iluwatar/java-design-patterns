package com.iluwatar.serialized.lob;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests that the serialized LOB example runs without errors.
 */
public class AppTest {
    @Test
    void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
