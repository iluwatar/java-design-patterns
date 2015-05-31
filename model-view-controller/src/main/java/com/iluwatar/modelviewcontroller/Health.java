package com.iluwatar.modelviewcontroller;

/**
 * 
 * Health enumeration
 *
 */
public enum Health {
	
	HEALTHY("healthy"), WOUNDED("wounded"), DEAD("dead");
	
	private String title;
	
	Health(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
