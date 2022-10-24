package com.iluwatar.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests App class : src/main/java/com/iluwatar/component/App.java
 * General execution test of the application.
 */
class AppTest {

    @Test
    void shouldExecuteComponentWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
