package com.iluwatar;

/**
 * 
 * In State pattern the container object (Mammoth) has an internal state object (State) that
 * defines the current behavior. The state object can be changed to alter the
 * behavior.
 * 
 * In this example the mammoth changes its behavior as time passes by.
 * 
 */
public class App {

	public static void main(String[] args) {

		Mammoth mammoth = new Mammoth();
		mammoth.observe();
		mammoth.timePasses();
		mammoth.observe();
		mammoth.timePasses();
		mammoth.observe();

	}
}
