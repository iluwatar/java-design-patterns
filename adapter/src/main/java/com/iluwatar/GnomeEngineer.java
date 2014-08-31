package com.iluwatar;

/**
 * 
 * Adapter class. Adapts the interface of the
 * GoblinGlider into Engineer expected by the
 * client.
 *
 */
public class GnomeEngineer implements Engineer {

	private GoblinGlider glider;
	
	public GnomeEngineer() {
		glider = new GoblinGlider();
	}

	@Override
	public void operateDevice() {
		glider.attachGlider();
		glider.gainSpeed();
		glider.takeOff();
	}
	
}
