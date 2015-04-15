package com.iluwatar.spell;

import com.iluwatar.common.Dao;

/**
 * 
 * SpellDao interface.
 *
 */
public interface SpellDao extends Dao<Spell> {
	
	Spell findByName(String name);

}
