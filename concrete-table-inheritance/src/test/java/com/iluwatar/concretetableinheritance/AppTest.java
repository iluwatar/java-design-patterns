package com.iluwatar.concretetableinheritance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link App}.
 */
class AppTest {
  /**
   * App test.
   */
  @Test
  /* default */ void shouldExecuteWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}
