package com.iluwatar.spellbook;

import com.iluwatar.common.Dao;

public interface SpellbookDao extends Dao<Spellbook> {
	
	Spellbook findByName(String name);

}
