package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * The type App test.
 * CS304 Issue link: github.com/iluwatar/java-design-patterns/issues/1318
 */
class AppTest {
  /**
   * Should execute without exception.
   */
  @Test
  void shouldExecuteWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }

}
