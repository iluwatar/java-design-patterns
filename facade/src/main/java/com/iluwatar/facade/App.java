package com.iluwatar.facade;

/**
 * 
 * The Facade design pattern is often used when a system is very complex or difficult 
 * to understand because the system has a large number of interdependent classes or 
 * its source code is unavailable. This pattern hides the complexities of the larger 
 * system and provides a simpler interface to the client. It typically involves a single 
 * wrapper class which contains a set of members required by client. These members access 
 * the system on behalf of the facade client and hide the implementation details.
 * <p>
 * In this example the Facade is ({@link DwarvenGoldmineFacade}) and it provides a simpler 
 * interface to the goldmine subsystem.
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
