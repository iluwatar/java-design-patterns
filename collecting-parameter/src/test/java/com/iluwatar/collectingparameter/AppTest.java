package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
  /**
   * Checks whether {@link App} executes without throwing exception
   */
  @Test
  void executesWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}
