package com.iluwatar.component.impl;

import com.iluwatar.component.InputComponent;
import com.iluwatar.component.objects.GameObject;

public class BjornDemoInputComponent implements InputComponent {

	private static int WALK_ACCELERATION = 1;

	public void update(GameObject gameObj) {

		gameObj.setVelocity(gameObj.getVelocity() - WALK_ACCELERATION);

	}

}
