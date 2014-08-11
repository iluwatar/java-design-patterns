package com.iluwatar;

public class GnomeEngineerAdapter {

	private GoblinGlider glider;
	
	public GnomeEngineerAdapter() {
		glider = new GoblinGlider();
	}
	
	public void flyGoblinGlider() {
		glider.attachGlider();
		glider.gainSpeed();
		glider.takeOff();
	}
	
}
