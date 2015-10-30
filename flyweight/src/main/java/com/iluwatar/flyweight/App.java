package com.iluwatar.flyweight;

/**
 * 
 * Flyweight pattern is useful when the program needs a huge amount of objects.
 * It provides means to decrease resource usage by sharing object instances.
 * <p>
 * In this example {@link AlchemistShop} has great amount of potions on its shelves.
 * To fill the shelves {@link AlchemistShop} uses {@link PotionFactory} (which represents
 * the Flyweight in this example). Internally {@link PotionFactory} holds a map
 * of the potions and lazily creates new ones when requested.
 * <p>
 * To enable safe sharing, between clients and threads, Flyweight objects must 
 * be immutable. Flyweight objects are by definition value objects.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		AlchemistShop alchemistShop = new AlchemistShop();
		alchemistShop.enumerate();
	}
}
