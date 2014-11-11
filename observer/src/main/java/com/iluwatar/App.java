package com.iluwatar;

import com.iluwatar.generic.GHobbits;
import com.iluwatar.generic.GOrcs;
import com.iluwatar.generic.GWeather;

/**
 * 
 * Observer pattern defines one-to-many relationship between objects. The target
 * object sends change notifications to its registered observers.
 * 
 */
public class App {

	public static void main(String[] args) {

		Weather weather = new Weather();
		weather.addObserver(new Orcs());
		weather.addObserver(new Hobbits());

		weather.timePasses();
		weather.timePasses();
		weather.timePasses();
		weather.timePasses();

		// Generic observer inspired by Java Generics and Collection by Naftalin & Wadler
		GWeather gWeather = new GWeather();
		gWeather.addObserver(new GHobbits());
		gWeather.addObserver(new GOrcs());

		gWeather.timePasses();
		gWeather.timePasses();
		gWeather.timePasses();
		gWeather.timePasses();

	}
}
