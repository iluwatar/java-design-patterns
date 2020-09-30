package com.iluwatar.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarsFactoryTest {

	@Test
	void shouldReturnFerrariInstance() {
		final var ferrari = CarsFactory.getCar(CarType.FERRARI);
		assertTrue(ferrari instanceof Ferrari);
	}

}
