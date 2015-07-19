package com.iluwatar;

/**
 * 
 * Command for catapults.
 *
 */
public class CatapultCommand implements Command {

	@Override
	public void process() {
		new CatapultView().display();
	}
}
