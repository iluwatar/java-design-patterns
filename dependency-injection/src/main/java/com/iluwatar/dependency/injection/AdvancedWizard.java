package com.iluwatar.dependency.injection;

/**
 * 
 * AdvancedWizard implements inversion of control.
 * It depends on abstraction that can be injected through
 * its constructor.
 *
 */
public class AdvancedWizard implements Wizard {
	
	private Tobacco tobacco;

	public AdvancedWizard(Tobacco tobacco) {
		this.tobacco = tobacco;
	}

	@Override
	public void smoke() {
		tobacco.smoke(this);
	}
}
