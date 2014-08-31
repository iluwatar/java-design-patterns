package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Weather can be observed by implementing WeatherObserver
 * interface and registering as listener.
 *
 */
public class Weather {

	private WeatherType currentWeather;
	private List<WeatherObserver> observers;
	
	public Weather() {
		observers = new ArrayList<>();
		currentWeather = WeatherType.SUNNY;
	}
	
	public void addObserver(WeatherObserver obs) {
		observers.add(obs);
	}
	
	public void removeObserver(WeatherObserver obs) {
		observers.remove(obs);
	}
	
	public void timePasses() {
		switch (currentWeather) {
		case COLD:
			currentWeather = WeatherType.SUNNY;
			break;
		case RAINY:
			currentWeather = WeatherType.WINDY;
			break;
		case SUNNY:
			currentWeather = WeatherType.RAINY;
			break;
		case WINDY:
			currentWeather = WeatherType.COLD;
			break;
		default:
			break;
		}
		System.out.println("The weather now changes to " + currentWeather);
		notifyObservers();
	}
	
	private void notifyObservers() {
		for (WeatherObserver obs: observers) {
			obs.update(currentWeather);
		}
	}
}
