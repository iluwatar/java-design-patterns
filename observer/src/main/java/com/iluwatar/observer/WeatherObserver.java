package com.iluwatar.observer;

/**
 * 
 * Observer interface.
 * 
 */
public interface WeatherObserver {

  void update(WeatherType currentWeather);

}
