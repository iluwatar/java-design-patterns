package com.iluwatar.model.view.controller.with.observer;

/**
 * 
 * Nourishment enumeration
 *
 */
public enum Nourishment {

	SATURATED("saturated"), HUNGRY("hungry"), STARVING("starving");
	
	private String title;
	
	Nourishment(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}	
}
