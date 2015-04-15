package com.iluwatar;

public interface SpellDao extends Dao<Spell> {
	
	Spell findByName(String name);

}
