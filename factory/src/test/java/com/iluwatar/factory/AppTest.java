package com.iluwatar.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

	@Test
	void shouldExecuteWithoutExceptions() {
		assertDoesNotThrow(() -> App.main(new String[]{}));
	}

}
