package com.iluwatar;

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
