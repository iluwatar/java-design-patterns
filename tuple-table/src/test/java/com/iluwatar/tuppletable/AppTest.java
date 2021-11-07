package com.iluwatar.tuppletable;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {
    /**
     *Test for testing the connection ensuring the code works as per design
     */
    @Test
    public void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}