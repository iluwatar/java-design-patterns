package com.iluwatar.sessionfacade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void shouldExecuteApplicationWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

}