package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}
