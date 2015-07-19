package com.iluwatar;

/**
 * 
 * Default command in case the mapping is not successful.
 *
 */
public class UnknownCommand implements Command {

	@Override
	public void process() {
		new ErrorView().display();
	}
}
