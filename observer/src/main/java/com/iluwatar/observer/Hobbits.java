package com.iluwatar.observer;

/**
 * 
 * Hobbits
 *
 */
public class Hobbits implements WeatherObserver {

	@Override
	public void update(WeatherType currentWeather) {
		switch (currentWeather) {
		case COLD:
			System.out.println("The hobbits are shivering in the cold weather.");
			break;
		case RAINY:
			System.out.println("The hobbits look for cover from the rain.");
			break;
		case SUNNY:
			System.out.println("The happy hobbits bade in the warm sun.");
			break;
		case WINDY:
			System.out.println("The hobbits hold their hats tightly in the windy weather.");
			break;
		default:
			break;
		}
	}

}
