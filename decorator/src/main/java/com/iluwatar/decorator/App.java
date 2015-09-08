package com.iluwatar.decorator;

/**
 * 
 * Decorator pattern is a more flexible alternative to subclassing. The decorator
 * class implements the same interface as the target and uses composition to
 * "decorate" calls to the target.
 * <p>
 * Using decorator pattern it is possible to change class behavior during
 * runtime, as the example shows.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		// simple troll
		System.out.println("A simple looking troll approaches.");
		Hostile troll = new Troll();
		troll.attack();
		troll.fleeBattle();

		// change the behavior of the simple troll by adding a decorator
		System.out.println("\nA smart looking troll surprises you.");
		Hostile smart = new SmartTroll(troll);
		smart.attack();
		smart.fleeBattle();
	}
}
