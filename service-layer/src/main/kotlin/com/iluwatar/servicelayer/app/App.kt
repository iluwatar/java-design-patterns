/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

// ABOUTME: Main application demonstrating the service layer pattern with wizard/spellbook domain.
// ABOUTME: Shows how clients interact with the service layer to coordinate data access and business logic.
package com.iluwatar.servicelayer.app

import com.iluwatar.servicelayer.magic.MagicServiceImpl
import com.iluwatar.servicelayer.spell.Spell
import com.iluwatar.servicelayer.spell.SpellDaoImpl
import com.iluwatar.servicelayer.spellbook.Spellbook
import com.iluwatar.servicelayer.spellbook.SpellbookDaoImpl
import com.iluwatar.servicelayer.wizard.Wizard
import com.iluwatar.servicelayer.wizard.WizardDaoImpl
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

const val BOOK_OF_IDORES = "Book of Idores"

/**
 * Service layer defines an application's boundary with a layer of services that establishes a set
 * of available operations and coordinates the application's response in each operation.
 *
 * Enterprise applications typically require different kinds of interfaces to the data they store
 * and the logic they implement: data loaders, user interfaces, integration gateways, and others.
 * Despite their different purposes, these interfaces often need common interactions with the
 * application to access and manipulate its data and invoke its business logic. The interactions may
 * be complex, involving transactions across multiple resources and the coordination of several
 * responses to an action. Encoding the logic of the interactions separately in each interface
 * causes a lot of duplication.
 *
 * The example application demonstrates interactions between a client ([main]) and a service
 * ([com.iluwatar.servicelayer.magic.MagicService]). The service is implemented with 3-layer
 * architecture (entity, dao, service). For persistence the example uses in-memory H2 database
 * which is populated on each application startup.
 */
fun main() {
    // populate the in-memory database
    initData()
    // query the data using the service
    queryData()
}

/**
 * Initialize data.
 */
fun initData() {
    // spells
    val spell1 = Spell("Ice dart")
    val spell2 = Spell("Invisibility")
    val spell3 = Spell("Stun bolt")
    val spell4 = Spell("Confusion")
    val spell5 = Spell("Darkness")
    val spell6 = Spell("Fireball")
    val spell7 = Spell("Enchant weapon")
    val spell8 = Spell("Rock armour")
    val spell9 = Spell("Light")
    val spell10 = Spell("Bee swarm")
    val spell11 = Spell("Haste")
    val spell12 = Spell("Levitation")
    val spell13 = Spell("Magic lock")
    val spell14 = Spell("Summon hell bat")
    val spell15 = Spell("Water walking")
    val spell16 = Spell("Magic storm")
    val spell17 = Spell("Entangle")
    val spellDao = SpellDaoImpl()
    spellDao.persist(spell1)
    spellDao.persist(spell2)
    spellDao.persist(spell3)
    spellDao.persist(spell4)
    spellDao.persist(spell5)
    spellDao.persist(spell6)
    spellDao.persist(spell7)
    spellDao.persist(spell8)
    spellDao.persist(spell9)
    spellDao.persist(spell10)
    spellDao.persist(spell11)
    spellDao.persist(spell12)
    spellDao.persist(spell13)
    spellDao.persist(spell14)
    spellDao.persist(spell15)
    spellDao.persist(spell16)
    spellDao.persist(spell17)

    // spellbooks
    val spellbookDao = SpellbookDaoImpl()
    val spellbook1 = Spellbook("Book of Orgymon")
    spellbookDao.persist(spellbook1)
    spellbook1.addSpell(spell1)
    spellbook1.addSpell(spell2)
    spellbook1.addSpell(spell3)
    spellbook1.addSpell(spell4)
    spellbookDao.merge(spellbook1)
    val spellbook2 = Spellbook("Book of Aras")
    spellbookDao.persist(spellbook2)
    spellbook2.addSpell(spell5)
    spellbook2.addSpell(spell6)
    spellbookDao.merge(spellbook2)
    val spellbook3 = Spellbook("Book of Kritior")
    spellbookDao.persist(spellbook3)
    spellbook3.addSpell(spell7)
    spellbook3.addSpell(spell8)
    spellbook3.addSpell(spell9)
    spellbookDao.merge(spellbook3)
    val spellbook4 = Spellbook("Book of Tamaex")
    spellbookDao.persist(spellbook4)
    spellbook4.addSpell(spell10)
    spellbook4.addSpell(spell11)
    spellbook4.addSpell(spell12)
    spellbookDao.merge(spellbook4)
    val spellbook5 = Spellbook(BOOK_OF_IDORES)
    spellbookDao.persist(spellbook5)
    spellbook5.addSpell(spell13)
    spellbookDao.merge(spellbook5)
    val spellbook6 = Spellbook("Book of Opaen")
    spellbookDao.persist(spellbook6)
    spellbook6.addSpell(spell14)
    spellbook6.addSpell(spell15)
    spellbookDao.merge(spellbook6)
    val spellbook7 = Spellbook("Book of Kihione")
    spellbookDao.persist(spellbook7)
    spellbook7.addSpell(spell16)
    spellbook7.addSpell(spell17)
    spellbookDao.merge(spellbook7)

    // wizards
    val wizardDao = WizardDaoImpl()
    val wizard1 = Wizard("Aderlard Boud")
    wizardDao.persist(wizard1)
    wizard1.addSpellbook(spellbookDao.findByName("Book of Orgymon"))
    wizard1.addSpellbook(spellbookDao.findByName("Book of Aras"))
    wizardDao.merge(wizard1)
    val wizard2 = Wizard("Anaxis Bajraktari")
    wizardDao.persist(wizard2)
    wizard2.addSpellbook(spellbookDao.findByName("Book of Kritior"))
    wizard2.addSpellbook(spellbookDao.findByName("Book of Tamaex"))
    wizardDao.merge(wizard2)
    val wizard3 = Wizard("Xuban Munoa")
    wizardDao.persist(wizard3)
    wizard3.addSpellbook(spellbookDao.findByName(BOOK_OF_IDORES))
    wizard3.addSpellbook(spellbookDao.findByName("Book of Opaen"))
    wizardDao.merge(wizard3)
    val wizard4 = Wizard("Blasius Dehooge")
    wizardDao.persist(wizard4)
    wizard4.addSpellbook(spellbookDao.findByName("Book of Kihione"))
    wizardDao.merge(wizard4)
}

/**
 * Query the data.
 */
fun queryData() {
    val wizardDao = WizardDaoImpl()
    val spellbookDao = SpellbookDaoImpl()
    val spellDao = SpellDaoImpl()
    val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)
    logger.info { "Enumerating all wizards" }
    service.findAllWizards().map { it.name }.forEach { logger.info { it } }
    logger.info { "Enumerating all spellbooks" }
    service.findAllSpellbooks().map { it.name }.forEach { logger.info { it } }
    logger.info { "Enumerating all spells" }
    service.findAllSpells().map { it.name }.forEach { logger.info { it } }
    logger.info { "Find wizards with spellbook 'Book of Idores'" }
    val wizardsWithSpellbook = service.findWizardsWithSpellbook(BOOK_OF_IDORES)
    wizardsWithSpellbook.forEach { w -> logger.info { "${w.name} has 'Book of Idores'" } }
    logger.info { "Find wizards with spell 'Fireball'" }
    val wizardsWithSpell = service.findWizardsWithSpell("Fireball")
    wizardsWithSpell.forEach { w -> logger.info { "${w.name} has 'Fireball'" } }
}
