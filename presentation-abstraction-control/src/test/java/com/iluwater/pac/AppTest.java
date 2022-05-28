package com.iluwater.pac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Application test for not throw.
 */
class AppTest {

  @Test
  void shouldExecuteApplicationWithoutException() {
    App app = new App();
    assertDoesNotThrow(() -> app.main(new String[]{}));
  }
}
