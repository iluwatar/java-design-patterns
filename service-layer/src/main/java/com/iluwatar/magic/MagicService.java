package com.iluwatar.magic;

import java.util.List;

import com.iluwatar.spell.Spell;
import com.iluwatar.spellbook.Spellbook;
import com.iluwatar.wizard.Wizard;


/**
 * 
 * Service interface.
 *
 */
public interface MagicService {

	List<Wizard> findAllWizards();

	List<Spellbook> findAllSpellbooks();
	
	List<Spell> findAllSpells();

	List<Wizard> findWizardsWithSpellbook(String name);

	List<Wizard> findWizardsWithSpell(String name);
}
