package com.iluwatar.metamapping;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests that metadata mapping example runs without errors.
 */
class AppTest {
  /**
   * Issue: Add at least one assertion to this test case.
   *
   * Solution: Inserted assertion to check whether the execution of the main method in {@link App#main(String[])}
   * throws an exception.
   */
  @Test
  void shouldExecuteMetaMappingWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}
