package com.iluwatar;

import java.util.List;

public class MagicServiceImpl implements MagicService {

	@Override
	public List<Wizard> findAllWizards() {
		return new WizardDaoImpl().findAll();
	}

	@Override
	public List<Spellbook> findAllSpellbooks() {
		return new SpellbookDaoImpl().findAll();
	}

	@Override
	public List<Spell> findAllSpells() {
		return new SpellDaoImpl().findAll();
	}
}
