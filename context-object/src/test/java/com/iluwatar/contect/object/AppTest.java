package com.iluwatar.contect.object;

import com.iluwatar.context.object.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

  /**
   * Test example app runs without error.
   */
  @Test
  void shouldExecuteWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }
}
