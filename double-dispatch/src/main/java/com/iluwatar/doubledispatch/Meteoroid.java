package com.iluwatar.doubledispatch;

/**
 * 
 * Meteoroid game object
 *
 */
public class Meteoroid extends GameObject {

	public Meteoroid(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
	}

	@Override
	public void collision(GameObject gameObject) {
		gameObject.collisionResolve(this);
	}

	@Override
	public void collisionResolve(FlamingAsteroid asteroid) {
		System.out.println(String.format("%s hits %s.", asteroid.getClass().getSimpleName(), this.getClass().getSimpleName()));
	}

	@Override
	public void collisionResolve(Meteoroid meteoroid) {
		System.out.println(String.format("%s hits %s.", meteoroid.getClass().getSimpleName(), this.getClass().getSimpleName()));
	}

	@Override
	public void collisionResolve(SpaceStationMir mir) {
		System.out.println(String.format("%s hits %s.", mir.getClass().getSimpleName(), this.getClass().getSimpleName()));
	}

	@Override
	public void collisionResolve(SpaceStationIss iss) {
		System.out.println(String.format("%s hits %s.", iss.getClass().getSimpleName(), this.getClass().getSimpleName()));
	}	
}
