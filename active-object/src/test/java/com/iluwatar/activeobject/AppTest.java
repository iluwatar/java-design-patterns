package com.iluwatar.activeobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;


class AppTest {

	  @Test
	  void shouldExecuteApplicationWithoutException() {
	    assertDoesNotThrow(() -> App.main(new String[]{}));
	  }
}
