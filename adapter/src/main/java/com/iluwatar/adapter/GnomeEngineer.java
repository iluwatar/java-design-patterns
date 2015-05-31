package com.iluwatar.adapter;

/**
 * 
 * Adapter class. Adapts the interface of the device (GoblinGlider) into
 * Engineer interface expected by the client (GnomeEngineeringManager).
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
