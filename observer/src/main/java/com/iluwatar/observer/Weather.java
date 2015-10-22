package com.iluwatar.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Weather can be observed by implementing {@link WeatherObserver} interface and
 * registering as listener.
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
		WeatherType[] enumValues = WeatherType.values();
		currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
		System.out.println("The weather changed to " + currentWeather + ".");
		notifyObservers();
	}

	private void notifyObservers() {
		for (WeatherObserver obs : observers) {
			obs.update(currentWeather);
		}
	}
}
