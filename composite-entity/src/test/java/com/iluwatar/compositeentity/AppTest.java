package com.iluwatar.compositeentity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * com.iluwatar.compositeentity.App running test
 */
class AppTest {

  /**
   * Issue: Add at least one assertion to this test case.
   * <p>
   * Solution: Inserted assertion to check whether the execution of the main method in {@link
   * App#main(String[])} throws an exception.
   */

  @Test
  void shouldExecuteApplicationWithoutException() {

    assertDoesNotThrow(() -> App.main(new String[]{}));

  }
}
