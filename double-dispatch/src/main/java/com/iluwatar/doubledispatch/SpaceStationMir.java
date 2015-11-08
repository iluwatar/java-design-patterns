package com.iluwatar.doubledispatch;

/**
 * 
 * Space station Mir game object
 *
 */
public class SpaceStationMir extends GameObject {

	public SpaceStationMir(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
	}

	@Override
	public void collision(GameObject gameObject) {
		gameObject.collisionResolve(this);
	}

	@Override
	public void collisionResolve(FlamingAsteroid asteroid) {
		System.out.println(String.format("%s hits %s. %s is damaged! %s is set on fire!", 
				asteroid.getClass().getSimpleName(), this.getClass().getSimpleName(), 
				this.getClass().getSimpleName(), this.getClass().getSimpleName()));
		setDamaged(true);
		setOnFire(true);
	}

	@Override
	public void collisionResolve(Meteoroid meteoroid) {
		System.out.println(String.format("%s hits %s. %s is damaged!", 
				meteoroid.getClass().getSimpleName(), this.getClass().getSimpleName(), 
				this.getClass().getSimpleName()));
		setDamaged(true);
	}

	@Override
	public void collisionResolve(SpaceStationMir mir) {
		System.out.println(String.format("%s hits %s. %s is damaged!", 
				mir.getClass().getSimpleName(), this.getClass().getSimpleName(), 
				this.getClass().getSimpleName()));
		setDamaged(true);
	}

	@Override
	public void collisionResolve(SpaceStationIss iss) {
		System.out.println(String.format("%s hits %s. %s is damaged!", 
				iss.getClass().getSimpleName(), this.getClass().getSimpleName(), 
				this.getClass().getSimpleName()));
		setDamaged(true);
	}	
}
