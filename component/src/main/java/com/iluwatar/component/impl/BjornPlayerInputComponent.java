package com.iluwatar.component.impl;

import com.iluwatar.component.InputComponent;
import com.iluwatar.component.objects.GameObject;

public class BjornPlayerInputComponent implements InputComponent {

	private static int WALK_ACCELERATION = 1;

	public void update(GameObject gameObj) {
		switch (gameObj.getJoyStickDirection()) {
		case DIR_LEFT:
			gameObj.setVelocity(gameObj.getVelocity() - WALK_ACCELERATION);
			break;

		case DIR_RIGHT:
			gameObj.setVelocity(gameObj.getVelocity() + WALK_ACCELERATION);
			break;
		}
	}
}
