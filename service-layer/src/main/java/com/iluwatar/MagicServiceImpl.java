package com.iluwatar;

import java.util.List;

public class MagicServiceImpl implements MagicService {
	
	private WizardDao wizardDao;
	private SpellbookDao spellbookDao;
	private SpellDao spellDao;

	public MagicServiceImpl(WizardDao wizardDao, SpellbookDao spellbookDao, SpellDao spellDao) {
		this.wizardDao = wizardDao;
		this.spellbookDao = spellbookDao;
		this.spellDao = spellDao;
	}

	@Override
	public List<Wizard> findAllWizards() {
		return wizardDao.findAll();
	}

	@Override
	public List<Spellbook> findAllSpellbooks() {
		return spellbookDao.findAll();
	}

	@Override
	public List<Spell> findAllSpells() {
		return spellDao.findAll();
	}
}
