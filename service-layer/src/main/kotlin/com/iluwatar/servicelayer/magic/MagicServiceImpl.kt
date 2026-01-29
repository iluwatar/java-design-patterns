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

// ABOUTME: Implementation of MagicService that coordinates DAOs to fulfill business operations.
// ABOUTME: Demonstrates the service layer pattern by encapsulating data access behind a service facade.
package com.iluwatar.servicelayer.magic

import com.iluwatar.servicelayer.spell.Spell
import com.iluwatar.servicelayer.spell.SpellDao
import com.iluwatar.servicelayer.spellbook.Spellbook
import com.iluwatar.servicelayer.spellbook.SpellbookDao
import com.iluwatar.servicelayer.wizard.Wizard
import com.iluwatar.servicelayer.wizard.WizardDao

/**
 * Service implementation.
 */
class MagicServiceImpl(
    private val wizardDao: WizardDao,
    private val spellbookDao: SpellbookDao,
    private val spellDao: SpellDao
) : MagicService {

    override fun findAllWizards(): List<Wizard> = wizardDao.findAll()

    override fun findAllSpellbooks(): List<Spellbook> = spellbookDao.findAll()

    override fun findAllSpells(): List<Spell> = spellDao.findAll()

    override fun findWizardsWithSpellbook(name: String): List<Wizard> {
        val spellbook = spellbookDao.findByName(name)
        return spellbook?.wizards?.toList() ?: emptyList()
    }

    override fun findWizardsWithSpell(name: String): List<Wizard> {
        val spell = spellDao.findByName(name)
        val spellbook = spell?.spellbook
        return spellbook?.wizards?.toList() ?: emptyList()
    }
}
