package com.iluwatar;

/**
 * 
 * There are two variations of the Adapter pattern: The class adapter implements
 * the adaptee's interface whereas the object adapter uses composition to
 * contain the adaptee in the adapter object. This example uses the object
 * adapter approach.
 * 
 * The Adapter (GnomeEngineer) converts the interface of the target class
 * (GoblinGlider) into a suitable one expected by the client
 * (GnomeEngineeringManager).
 * 
 */
public class App {

	public static void main(String[] args) {
		GnomeEngineeringManager manager = new GnomeEngineeringManager();
		manager.operateDevice();
	}
}
