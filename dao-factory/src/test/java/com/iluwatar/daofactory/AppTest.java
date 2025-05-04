package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** {@link App} */
class AppTest {
  /** Test ensure that no exception when execute main function */
  @Test
  void shouldExecuteDaoWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }
}
