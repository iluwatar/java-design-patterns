package com.iluwatar;

public class FlamingAsteroid extends Meteoroid {

	public FlamingAsteroid(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
		setOnFire(true);
	}
}
