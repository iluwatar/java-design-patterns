package com.iluwatar.servicelayer.servicelayer.spell;

import com.iluwatar.servicelayer.common.Dao;

/**
 * 
 * SpellDao interface.
 *
 */
public interface SpellDao extends Dao<Spell> {
	
	Spell findByName(String name);

}
