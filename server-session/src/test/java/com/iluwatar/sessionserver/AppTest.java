package com.iluwatar.sessionserver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class AppTest {

  @Test
  void testMain() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}