package com.iluwatar.client.session;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class AppTest {

  @Test
  public void appStartsWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }
}
