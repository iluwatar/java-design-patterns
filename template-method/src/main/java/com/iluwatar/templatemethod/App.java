package com.iluwatar.templatemethod;

/**
 * 
 * Template Method defines a skeleton for an algorithm. The algorithm subclasses 
 * provide implementation for the blank parts.
 * <p>
 * In this example {@link HalflingThief} contains {@link StealingMethod} that can be changed. 
 * First the thief hits with {@link HitAndRunMethod} and then with {@link SubtleMethod}.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		HalflingThief thief = new HalflingThief(new HitAndRunMethod());
		thief.steal();
		thief.changeMethod(new SubtleMethod());
		thief.steal();
	}
}
