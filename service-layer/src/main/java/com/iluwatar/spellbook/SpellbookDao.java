package com.iluwatar.spellbook;

import com.iluwatar.common.Dao;

/**
 * 
 * SpellbookDao interface.
 *
 */
public interface SpellbookDao extends Dao<Spellbook> {
	
	Spellbook findByName(String name);

}
