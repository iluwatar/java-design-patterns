package com.iluwatar;

public enum WeatherType {

	SUNNY, RAINY, WINDY, COLD;

	public String toString() {
		return this.name().toLowerCase();
	};

}
