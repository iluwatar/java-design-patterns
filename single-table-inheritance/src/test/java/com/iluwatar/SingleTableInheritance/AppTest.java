package com.iluwatar.SingleTableInheritance;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for App class.
 */
public class AppTest {

    @Test
    public void shouldExecuteAppWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

}