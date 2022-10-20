package com.iluwatar.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * General execution test of the application.
 */
public class AppTest {

    @Test
    void shouldExecuteComponentWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
