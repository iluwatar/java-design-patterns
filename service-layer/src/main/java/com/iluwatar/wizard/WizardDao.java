package com.iluwatar.wizard;

import com.iluwatar.common.Dao;

/**
 * 
 * WizardDao interface.
 *
 */
public interface WizardDao extends Dao<Wizard> {
	
	Wizard findByName(String name);

}
