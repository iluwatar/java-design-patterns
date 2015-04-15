package com.iluwatar;

public interface WizardDao extends Dao<Wizard> {
	
	Wizard findByName(String name);

}
