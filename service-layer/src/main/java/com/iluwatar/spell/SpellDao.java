package com.iluwatar.spell;

import com.iluwatar.common.Dao;

public interface SpellDao extends Dao<Spell> {
	
	Spell findByName(String name);

}
