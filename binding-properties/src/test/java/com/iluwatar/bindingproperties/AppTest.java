package com.iluwatar.bindingproperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Application Test Entry
 */

class AppTest {

    /**
     * Inserted assertion to check whether the execution of the main method in {@link App#main(String[])}
     * throws an exception.
     */
    @Test
    void shouldExecuteApplicationWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}