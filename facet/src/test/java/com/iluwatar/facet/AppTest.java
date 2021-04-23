package com.iluwatar.facet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Application test
 */
class AppTest {

  /**
   * Issue: Add at least one assertion to this test case.
   * <p>
   * Solution: Inserted assertion to check whether the execution of the main method in {@link App#main(String[])}
   * throws an exception.
   */

  @Test
  void shouldExecuteApplicationWithoutException() {
    Assertions.assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}