package com.iluwatar;

public abstract class GameObject extends Rectangle {
	
	public GameObject(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
