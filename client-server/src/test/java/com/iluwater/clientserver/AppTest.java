package com.iluwater.clientserver;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.iluwater.clientserver.App;

public class AppTest {
    @Test
    void shouldExecuteApplicationWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
