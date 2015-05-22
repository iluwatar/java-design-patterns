package com.iluwatar;

public class SimpleWizard implements Wizard {
	
	private OldTobyTobacco tobacco = new OldTobyTobacco();
	
	public void smoke() {
		tobacco.smoke(this);
	}
}
