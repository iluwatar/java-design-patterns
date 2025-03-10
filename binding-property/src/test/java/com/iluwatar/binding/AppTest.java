package com.iluwatar.binding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

  @Test
  public void testAppRunsWithoutExceptions() {
    assertDoesNotThrow(() -> {
      com.iluwatar.binding.App.main(new String[]{});
    });
  }
}

