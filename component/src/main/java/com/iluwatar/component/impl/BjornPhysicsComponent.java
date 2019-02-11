package com.iluwatar.component.impl;

import com.iluwatar.component.PhysicsComponent;
import com.iluwatar.component.objects.GameObject;

/**
 * Component for computations 
 *
 */
public class BjornPhysicsComponent implements PhysicsComponent {

	public void update(GameObject gameObj) {
		gameObj.setxCoordinate(gameObj.getxCoordinate() + gameObj.getVelocity());
		gameObj.setyCoordinate(gameObj.getyCoordinate() + gameObj.getVelocity());
	}
}
