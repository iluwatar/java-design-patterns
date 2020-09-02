package com.iluwatar.simplefactory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarSimpleFactoryTest {

	@Test
	void shouldReturnFerrariInstance() {
		final var ferrari = CarSimpleFactory.getCar(CarSimpleFactory.CarType.FERRARI);
		assertTrue(ferrari instanceof Ferrari);
	}

}
