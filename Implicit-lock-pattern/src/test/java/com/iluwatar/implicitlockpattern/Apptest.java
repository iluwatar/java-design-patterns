package com.iluwatar.implicitlockpattern;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

  @Test
  void shouldExecuteApplicationWithoutException() {
    // Verifying that the main method of the application runs without any exceptions
    assertDoesNotThrow(() -> App.main(new String[]{}), "The application should run without throwing any exceptions");
  }
}
