package com.iluwatar.adapter;

/**
 * 
 * There are two variations of the Adapter pattern: The class adapter implements
 * the adaptee's interface whereas the object adapter uses composition to
 * contain the adaptee in the adapter object. This example uses the object
 * adapter approach.
 * <p>
 * The Adapter ({@link GnomeEngineer}) converts the interface of the target class
 * ({@link GoblinGlider}) into a suitable one expected by the client
 * ({@link GnomeEngineeringManager}).
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		Engineer manager = new GnomeEngineeringManager();
		manager.operateDevice();
	}
}
