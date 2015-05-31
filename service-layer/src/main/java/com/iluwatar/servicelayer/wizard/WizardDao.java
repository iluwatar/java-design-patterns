package com.iluwatar.servicelayer.wizard;

import com.iluwatar.servicelayer.common.Dao;

/**
 * 
 * WizardDao interface.
 *
 */
public interface WizardDao extends Dao<Wizard> {
	
	Wizard findByName(String name);

}
