package com.iluwatar.state;

/**
 * 
 * In State pattern the container object ({@link Mammoth}) has an internal state object ({@link State}) that
 * defines the current behavior. The state object can be changed to alter the
 * behavior.
 * <p>
 * In this example the {@link Mammoth} changes its behavior as time passes by.
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
