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

// ABOUTME: Unit tests for MagicServiceImpl using MockK for mocking DAOs.
// ABOUTME: Verifies service layer correctly delegates to and coordinates DAOs.
package com.iluwatar.servicelayer.magic

import com.iluwatar.servicelayer.spell.Spell
import com.iluwatar.servicelayer.spell.SpellDao
import com.iluwatar.servicelayer.spellbook.Spellbook
import com.iluwatar.servicelayer.spellbook.SpellbookDao
import com.iluwatar.servicelayer.wizard.Wizard
import com.iluwatar.servicelayer.wizard.WizardDao
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * MagicServiceImplTest
 */
class MagicServiceImplTest {

    @Test
    fun testFindAllWizards() {
        val wizardDao = mockk<WizardDao>(relaxed = true)
        val spellbookDao = mockk<SpellbookDao>(relaxed = true)
        val spellDao = mockk<SpellDao>(relaxed = true)

        val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)

        service.findAllWizards()
        verify(exactly = 1) { wizardDao.findAll() }
        confirmVerified(wizardDao, spellbookDao, spellDao)
    }

    @Test
    fun testFindAllSpellbooks() {
        val wizardDao = mockk<WizardDao>(relaxed = true)
        val spellbookDao = mockk<SpellbookDao>(relaxed = true)
        val spellDao = mockk<SpellDao>(relaxed = true)

        val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)

        service.findAllSpellbooks()
        verify(exactly = 1) { spellbookDao.findAll() }
        confirmVerified(wizardDao, spellbookDao, spellDao)
    }

    @Test
    fun testFindAllSpells() {
        val wizardDao = mockk<WizardDao>(relaxed = true)
        val spellbookDao = mockk<SpellbookDao>(relaxed = true)
        val spellDao = mockk<SpellDao>(relaxed = true)

        val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)

        service.findAllSpells()
        verify(exactly = 1) { spellDao.findAll() }
        confirmVerified(wizardDao, spellbookDao, spellDao)
    }

    @Test
    fun testFindWizardsWithSpellbook() {
        val bookname = "bookname"
        val spellbook = mockk<Spellbook>()
        val wizards = mutableSetOf(mockk<Wizard>(), mockk<Wizard>(), mockk<Wizard>())
        every { spellbook.wizards } returns wizards

        val spellbookDao = mockk<SpellbookDao>()
        every { spellbookDao.findByName(bookname) } returns spellbook

        val wizardDao = mockk<WizardDao>(relaxed = true)
        val spellDao = mockk<SpellDao>(relaxed = true)

        val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)

        val result = service.findWizardsWithSpellbook(bookname)
        verify(exactly = 1) { spellbookDao.findByName(bookname) }
        verify(exactly = 1) { spellbook.wizards }

        assertNotNull(result)
        assertEquals(3, result.size)

        confirmVerified(wizardDao, spellbookDao, spellDao)
    }

    @Test
    fun testFindWizardsWithSpell() {
        val wizards = mutableSetOf(mockk<Wizard>(), mockk<Wizard>(), mockk<Wizard>())
        val spellbook = mockk<Spellbook>()
        every { spellbook.wizards } returns wizards

        val spellbookDao = mockk<SpellbookDao>(relaxed = true)
        val wizardDao = mockk<WizardDao>(relaxed = true)

        val spell = mockk<Spell>()
        every { spell.spellbook } returns spellbook

        val spellName = "spellname"
        val spellDao = mockk<SpellDao>()
        every { spellDao.findByName(spellName) } returns spell

        val service = MagicServiceImpl(wizardDao, spellbookDao, spellDao)

        val result = service.findWizardsWithSpell(spellName)
        verify(exactly = 1) { spellDao.findByName(spellName) }
        verify(exactly = 1) { spellbook.wizards }

        assertNotNull(result)
        assertEquals(3, result.size)

        confirmVerified(wizardDao, spellbookDao, spellDao)
    }
}
