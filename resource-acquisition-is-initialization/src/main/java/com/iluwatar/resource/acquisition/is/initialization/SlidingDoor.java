package com.iluwatar.resource.acquisition.is.initialization;

/**
 * 
 * SlidingDoor resource
 *
 */
public class SlidingDoor implements AutoCloseable {

	public SlidingDoor() {
		System.out.println("Sliding door opens.");
	}
	
	@Override
	public void close() throws Exception {
		System.out.println("Sliding door closes.");
	}
}
