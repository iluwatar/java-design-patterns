package com.iluwatar.component.objects;

public class Sprite {

	private int dx;
	private int dy;

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void walkLeft() {
		setDx(-2);

	}

	public void walkRight() {
		setDx(2);

	}

	public void walkUp() {
		setDy(-2);

	}

	public void walkDown() {
		setDy(2);

	}

}
