package com.iluwatar.facade;

/**
 * 
 * Facade ({@link DwarvenGoldmineFacade}) provides simpler interface to subsystem.
 * <p>
 * http://en.wikipedia.org/wiki/Facade_pattern
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
		facade.startNewDay();
		facade.digOutGold();
		facade.endDay();
	}
}
