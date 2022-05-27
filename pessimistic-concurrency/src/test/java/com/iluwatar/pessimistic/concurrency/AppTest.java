package com.iluwatar.pessimistic.concurrency;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {
    @Test
    public void shouldExecuteAppWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
