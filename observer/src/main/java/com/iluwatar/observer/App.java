package com.iluwatar.observer;

import com.iluwatar.observer.generic.GHobbits;
import com.iluwatar.observer.generic.GOrcs;
import com.iluwatar.observer.generic.GWeather;

/**
 * 
 * Observer pattern defines one-to-many relationship between objects. The target
 * object sends change notifications to its registered observers.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		Weather weather = new Weather();
		weather.addObserver(new Orcs());
		weather.addObserver(new Hobbits());

		weather.timePasses();
		weather.timePasses();
		weather.timePasses();
		weather.timePasses();

		// Generic observer inspired by Java Generics and Collection by Naftalin & Wadler
		System.out.println("\n--Running generic version--");
		GWeather gWeather = new GWeather();
		gWeather.addObserver(new GOrcs());
		gWeather.addObserver(new GHobbits());

		gWeather.timePasses();
		gWeather.timePasses();
		gWeather.timePasses();
		gWeather.timePasses();
	}
}
