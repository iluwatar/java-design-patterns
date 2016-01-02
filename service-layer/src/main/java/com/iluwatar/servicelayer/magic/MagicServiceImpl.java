package com.iluwatar.servicelayer.magic;

import java.util.ArrayList;
import java.util.List;

import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spell.SpellDao;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.spellbook.SpellbookDao;
import com.iluwatar.servicelayer.wizard.Wizard;
import com.iluwatar.servicelayer.wizard.WizardDao;

/**
 * 
 * Service implementation.
 *
 */
public class MagicServiceImpl implements MagicService {

  private WizardDao wizardDao;
  private SpellbookDao spellbookDao;
  private SpellDao spellDao;

  /**
   * Constructor
   */
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

  @Override
  public List<Wizard> findWizardsWithSpellbook(String name) {
    Spellbook spellbook = spellbookDao.findByName(name);
    return new ArrayList<Wizard>(spellbook.getWizards());
  }

  @Override
  public List<Wizard> findWizardsWithSpell(String name) {
    Spell spell = spellDao.findByName(name);
    Spellbook spellbook = spell.getSpellbook();
    return new ArrayList<Wizard>(spellbook.getWizards());
  }
}
