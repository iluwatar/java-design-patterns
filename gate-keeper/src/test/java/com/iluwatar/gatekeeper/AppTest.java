package com.iluwatar.gatekeeper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * Created by anditty on 2021/4/22 2:41 下午
 */
class AppTest {
    @Test
    void shouldExecuteWithoutExceptions() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
