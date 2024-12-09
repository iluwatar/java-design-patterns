package com.iluwatar.implicitlock;

import com.iluwatar.App;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class AppTest {

  @Test
  void shouldExecuteApplicationWithoutException() {
    // Verifying that the main method of the application runs without any exceptions
    assertDoesNotThrow(() -> App.main(new String[]{}), "The application should run without throwing any exceptions");
  }
}
