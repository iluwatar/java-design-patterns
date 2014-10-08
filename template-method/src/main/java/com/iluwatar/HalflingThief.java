package com.iluwatar;

/**
 * 
 * Halfling thief uses StealingMethod to steal.
 * 
 */
public class HalflingThief {

	private StealingMethod method;

	public HalflingThief(StealingMethod method) {
		this.method = method;
	}

	public void steal() {
		method.steal();
	}

	public void changeMethod(StealingMethod method) {
		this.method = method;
	}
}
