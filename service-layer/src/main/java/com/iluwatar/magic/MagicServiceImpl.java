package com.iluwatar.magic;

import java.util.List;

import com.iluwatar.spell.Spell;
import com.iluwatar.spell.SpellDao;
import com.iluwatar.spellbook.Spellbook;
import com.iluwatar.spellbook.SpellbookDao;
import com.iluwatar.wizard.Wizard;
import com.iluwatar.wizard.WizardDao;

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
