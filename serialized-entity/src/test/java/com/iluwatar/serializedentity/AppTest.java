package com.iluwatar.serializedentity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests that Serialized Entity example runs without errors.
 */
class AppTest {

    /**
     * Issue: Add at least one assertion to this test case.
     *
     * Solution: Inserted assertion to check whether the execution of the main method in {@link App#main(String[])}
     * throws an exception.
     */

    @Test
    void shouldExecuteSerializedEntityWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
