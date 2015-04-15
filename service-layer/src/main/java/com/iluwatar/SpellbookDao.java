package com.iluwatar;

public interface SpellbookDao extends Dao<Spellbook> {
	
	Spellbook findByName(String name);

}
