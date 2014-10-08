package com.iluwatar;

/**
 * 
 * GnomeEngineering manager uses Engineer to operate devices.
 * 
 */
public class GnomeEngineeringManager implements Engineer {

	private Engineer engineer;

	public GnomeEngineeringManager() {
		engineer = new GnomeEngineer();
	}

	@Override
	public void operateDevice() {
		engineer.operateDevice();
	}
}
