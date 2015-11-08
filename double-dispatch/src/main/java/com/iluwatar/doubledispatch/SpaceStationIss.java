package com.iluwatar.doubledispatch;

/**
 * 
 * Space station ISS game object
 *
 */
public class SpaceStationIss extends SpaceStationMir {

	public SpaceStationIss(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
	}

	@Override
	public void collision(GameObject gameObject) {
		gameObject.collisionResolve(this);
	}
}
