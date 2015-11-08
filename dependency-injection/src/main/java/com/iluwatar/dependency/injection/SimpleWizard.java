package com.iluwatar.dependency.injection;

/**
 * 
 * Naive Wizard implementation violating the inversion of control principle.
 * It should depend on abstraction instead.
 *
 */
public class SimpleWizard implements Wizard {
	
	private OldTobyTobacco tobacco = new OldTobyTobacco();
	
	public void smoke() {
		tobacco.smoke(this);
	}
}
