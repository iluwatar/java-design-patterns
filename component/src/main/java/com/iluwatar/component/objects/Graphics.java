package com.iluwatar.component.objects;

public class Graphics {

	public void draw(Sprite sprite, int xCoord, int yCoord) {
		System.out.println("Drawing game object with cooredinates : DX " + sprite.getDx() + "DY :" + sprite.getDy()
				+ " X :" + xCoord + " Y: " + yCoord);
	}
}
