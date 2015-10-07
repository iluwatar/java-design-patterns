package com.iluwatar.adapter;

/**
 *
 * An adapter helps two incompatible interfaces to work together. This is the real 
 * world definition for an adapter. Interfaces may be incompatible but the inner 
 * functionality should suit the need. The Adapter design pattern allows otherwise 
 * incompatible classes to work together by converting the interface of one class 
 * into an interface expected by the clients.
 * <p>
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
