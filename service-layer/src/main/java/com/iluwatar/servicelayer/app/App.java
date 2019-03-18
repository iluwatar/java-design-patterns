/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.servicelayer.app;

import java.util.List;

import com.iluwatar.servicelayer.magic.MagicService;
import com.iluwatar.servicelayer.magic.MagicServiceImpl;
import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spell.SpellDao;
import com.iluwatar.servicelayer.spell.SpellDaoImpl;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.spellbook.SpellbookDao;
import com.iluwatar.servicelayer.spellbook.SpellbookDaoImpl;
import com.iluwatar.servicelayer.wizard.Wizard;
import com.iluwatar.servicelayer.wizard.WizardDao;
import com.iluwatar.servicelayer.wizard.WizardDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service layer defines an application's boundary with a layer of services that establishes a set
 * of available operations and coordinates the application's response in each operation.
 * <p>
 * Enterprise applications typically require different kinds of interfaces to the data they store
 * and the logic they implement: data loaders, user interfaces, integration gateways, and others.
 * Despite their different purposes, these interfaces often need common interactions with the
 * application to access and manipulate its data and invoke its business logic. The interactions may
 * be complex, involving transactions across multiple resources and the coordination of several
 * responses to an action. Encoding the logic of the interactions separately in each interface
 * causes a lot of duplication.
 * <p>
 * The example application demonstrates interactions between a client ({@link App}) and a service (
 * {@link MagicService}). The service is implemented with 3-layer architecture (entity, dao,
 * service). For persistence the example uses in-memory H2 database which is populated on each
 * application startup.
 *
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    // populate the in-memory database
    initData();
    // query the data using the service
    queryData();
  }

  /**
   * Initialize data
   */
  public static void initData() {
    // spells
    Spell spell1 = new Spell("Ice dart");
    Spell spell2 = new Spell("Invisibility");
    Spell spell3 = new Spell("Stun bolt");
    Spell spell4 = new Spell("Confusion");
    Spell spell5 = new Spell("Darkness");
    Spell spell6 = new Spell("Fireball");
    Spell spell7 = new Spell("Enchant weapon");
    Spell spell8 = new Spell("Rock armour");
    Spell spell9 = new Spell("Light");
    Spell spell10 = new Spell("Bee swarm");
    Spell spell11 = new Spell("Haste");
    Spell spell12 = new Spell("Levitation");
    Spell spell13 = new Spell("Magic lock");
    Spell spell14 = new Spell("Summon hell bat");
    Spell spell15 = new Spell("Water walking");
    Spell spell16 = new Spell("Magic storm");
    Spell spell17 = new Spell("Entangle");
    SpellDao spellDao = new SpellDaoImpl();
    spellDao.persist(spell1);
    spellDao.persist(spell2);
    spellDao.persist(spell3);
    spellDao.persist(spell4);
    spellDao.persist(spell5);
    spellDao.persist(spell6);
    spellDao.persist(spell7);
    spellDao.persist(spell8);
    spellDao.persist(spell9);
    spellDao.persist(spell10);
    spellDao.persist(spell11);
    spellDao.persist(spell12);
    spellDao.persist(spell13);
    spellDao.persist(spell14);
    spellDao.persist(spell15);
    spellDao.persist(spell16);
    spellDao.persist(spell17);

    // spellbooks
    SpellbookDao spellbookDao = new SpellbookDaoImpl();
    Spellbook spellbook1 = new Spellbook("Book of Orgymon");
    spellbookDao.persist(spellbook1);
    spellbook1.addSpell(spell1);
    spellbook1.addSpell(spell2);
    spellbook1.addSpell(spell3);
    spellbook1.addSpell(spell4);
    spellbookDao.merge(spellbook1);
    Spellbook spellbook2 = new Spellbook("Book of Aras");
    spellbookDao.persist(spellbook2);
    spellbook2.addSpell(spell5);
    spellbook2.addSpell(spell6);
    spellbookDao.merge(spellbook2);
    Spellbook spellbook3 = new Spellbook("Book of Kritior");
    spellbookDao.persist(spellbook3);
    spellbook3.addSpell(spell7);
    spellbook3.addSpell(spell8);
    spellbook3.addSpell(spell9);
    spellbookDao.merge(spellbook3);
    Spellbook spellbook4 = new Spellbook("Book of Tamaex");
    spellbookDao.persist(spellbook4);
    spellbook4.addSpell(spell10);
    spellbook4.addSpell(spell11);
    spellbook4.addSpell(spell12);
    spellbookDao.merge(spellbook4);
    Spellbook spellbook5 = new Spellbook("Book of Idores");
    spellbookDao.persist(spellbook5);
    spellbook5.addSpell(spell13);
    spellbookDao.merge(spellbook5);
    Spellbook spellbook6 = new Spellbook("Book of Opaen");
    spellbookDao.persist(spellbook6);
    spellbook6.addSpell(spell14);
    spellbook6.addSpell(spell15);
    spellbookDao.merge(spellbook6);
    Spellbook spellbook7 = new Spellbook("Book of Kihione");
    spellbookDao.persist(spellbook7);
    spellbook7.addSpell(spell16);
    spellbook7.addSpell(spell17);
    spellbookDao.merge(spellbook7);

    // wizards
    WizardDao wizardDao = new WizardDaoImpl();
    Wizard wizard1 = new Wizard("Aderlard Boud");
    wizardDao.persist(wizard1);
    wizard1.addSpellbook(spellbookDao.findByName("Book of Orgymon"));
    wizard1.addSpellbook(spellbookDao.findByName("Book of Aras"));
    wizardDao.merge(wizard1);
    Wizard wizard2 = new Wizard("Anaxis Bajraktari");
    wizardDao.persist(wizard2);
    wizard2.addSpellbook(spellbookDao.findByName("Book of Kritior"));
    wizard2.addSpellbook(spellbookDao.findByName("Book of Tamaex"));
    wizardDao.merge(wizard2);
    Wizard wizard3 = new Wizard("Xuban Munoa");
    wizardDao.persist(wizard3);
    wizard3.addSpellbook(spellbookDao.findByName("Book of Idores"));
    wizard3.addSpellbook(spellbookDao.findByName("Book of Opaen"));
    wizardDao.merge(wizard3);
    Wizard wizard4 = new Wizard("Blasius Dehooge");
    wizardDao.persist(wizard4);
    wizard4.addSpellbook(spellbookDao.findByName("Book of Kihione"));
    wizardDao.merge(wizard4);
  }

  /**
   * Query the data
   */
  public static void queryData() {
    MagicService service =
        new MagicServiceImpl(new WizardDaoImpl(), new SpellbookDaoImpl(), new SpellDaoImpl());
    LOGGER.info("Enumerating all wizards");
    for (Wizard w : service.findAllWizards()) {
      LOGGER.info(w.getName());
    }
    LOGGER.info("Enumerating all spellbooks");
    for (Spellbook s : service.findAllSpellbooks()) {
      LOGGER.info(s.getName());
    }
    LOGGER.info("Enumerating all spells");
    for (Spell s : service.findAllSpells()) {
      LOGGER.info(s.getName());
    }
    LOGGER.info("Find wizards with spellbook 'Book of Idores'");
    List<Wizard> wizardsWithSpellbook = service.findWizardsWithSpellbook("Book of Idores");
    for (Wizard w : wizardsWithSpellbook) {
      LOGGER.info("{} has 'Book of Idores'", w.getName());
    }
    LOGGER.info("Find wizards with spell 'Fireball'");
    List<Wizard> wizardsWithSpell = service.findWizardsWithSpell("Fireball");
    for (Wizard w : wizardsWithSpell) {
      LOGGER.info("{} has 'Fireball'", w.getName());
    }
  }
}
