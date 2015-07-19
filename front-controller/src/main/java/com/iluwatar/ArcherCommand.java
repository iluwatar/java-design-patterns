package com.iluwatar;

public class ArcherCommand implements Command {

	@Override
	public void process() {
		new ArcherView().display();
	}
}
