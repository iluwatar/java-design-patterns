package com.iluwatar;

import java.util.List;

public interface MagicService {

	List<Wizard> findAllWizards();

	List<Spellbook> findAllSpellbooks();
	
	List<Spell> findAllSpells();
	
}
