package com.iluwatar.component;

import com.iluwatar.component.enums.DirectionEnum;
import com.iluwatar.component.impl.BjornDemoInputComponent;
import com.iluwatar.component.impl.BjornGraphicsComponent;
import com.iluwatar.component.impl.BjornPhysicsComponent;
import com.iluwatar.component.impl.BjornPlayerInputComponent;
import com.iluwatar.component.objects.GameObject;

public class App {

	public static void main(String[] args) {

		// player object creation
		GameObject gameObject = new GameObject(new BjornPlayerInputComponent(), new BjornPhysicsComponent(),
				new BjornGraphicsComponent());
		gameObject.update(DirectionEnum.DIR_LEFT);
		gameObject.update(DirectionEnum.DIR_RIGHT);

		// demo object creation when screen is idle
		gameObject = new GameObject(new BjornDemoInputComponent(), new BjornPhysicsComponent(),
				new BjornGraphicsComponent());

		gameObject.update(DirectionEnum.DIR_LEFT);

	}
}
