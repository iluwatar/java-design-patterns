package com.iluwatar.dependencyinjection;

/**
 * 
 * Tobacco abstraction
 *
 */
public abstract class Tobacco {
	
	public void smoke(Wizard wizard) {
		System.out.println(String.format("%s smoking %s", wizard.getClass().getSimpleName(), this.getClass().getSimpleName()));
	}
}
