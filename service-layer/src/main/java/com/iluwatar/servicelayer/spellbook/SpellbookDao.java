package com.iluwatar.servicelayer.spellbook;

import com.iluwatar.servicelayer.common.Dao;

/**
 * 
 * SpellbookDao interface.
 *
 */
public interface SpellbookDao extends Dao<Spellbook> {
	
	Spellbook findByName(String name);

}
