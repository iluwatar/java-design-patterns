package com.iluwatar.facade;

/**
 * 
 * Facade (DwarvenGoldmineFacade) provides simpler interface to subsystem.
 * http://en.wikipedia.org/wiki/Facade_pattern
 * 
 */
public class App {

	public static void main(String[] args) {
		DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
		facade.startNewDay();
		facade.digOutGold();
		facade.endDay();
	}
}
