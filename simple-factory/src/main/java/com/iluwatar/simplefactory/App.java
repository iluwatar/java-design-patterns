package com.iluwatar.simplefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		Car car1 = CarSimpleFactory.getCar(CarSimpleFactory.carTypes.FORD);
		Car car2 = CarSimpleFactory.getCar(CarSimpleFactory.carTypes.FERRARI);
		LOGGER.info(car1.getDescription());
		LOGGER.info(car2.getDescription());
	}
}
