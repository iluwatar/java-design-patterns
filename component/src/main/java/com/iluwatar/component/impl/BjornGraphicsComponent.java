package com.iluwatar.component.impl;

import com.iluwatar.component.GraphicsComponent;
import com.iluwatar.component.objects.GameObject;
import com.iluwatar.component.objects.Graphics;
import com.iluwatar.component.objects.Sprite;

public class BjornGraphicsComponent implements GraphicsComponent {
	Sprite sprite;
	Graphics graphics;

	public void update(GameObject gameObject) {
		sprite = new Sprite();
		graphics = new Graphics();
		if (gameObject.getVelocity() < 0) {
			sprite.walkLeft();
		} else if (gameObject.getVelocity() > 0) {
			sprite.walkRight();
		}

		graphics.draw(sprite, gameObject.getxCoordinate(), gameObject.getyCoordinate());
	}

}
