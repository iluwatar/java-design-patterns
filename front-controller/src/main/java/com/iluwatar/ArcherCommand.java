package com.iluwatar;

/**
 * 
 * Command for archers.
 *
 */
public class ArcherCommand implements Command {

	@Override
	public void process() {
		new ArcherView().display();
	}
}
