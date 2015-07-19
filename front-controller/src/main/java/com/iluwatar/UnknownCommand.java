package com.iluwatar;

public class UnknownCommand implements Command {

	@Override
	public void process() {
		System.out.println("Error 500");
	}
}
