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
package com.iluwatar.servicelayer.magic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spell.SpellDao;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.spellbook.SpellbookDao;
import com.iluwatar.servicelayer.wizard.Wizard;
import com.iluwatar.servicelayer.wizard.WizardDao;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** MagicServiceImplTest */
class MagicServiceImplTest {

  @Test
  void testFindAllWizards() {
    final var wizardDao = mock(WizardDao.class);
    final var spellbookDao = mock(SpellbookDao.class);
    final var spellDao = mock(SpellDao.class);

    final var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyNoInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllWizards();
    verify(wizardDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  void testFindAllSpellbooks() {
    final var wizardDao = mock(WizardDao.class);
    final var spellbookDao = mock(SpellbookDao.class);
    final var spellDao = mock(SpellDao.class);

    final var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyNoInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllSpellbooks();
    verify(spellbookDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  void testFindAllSpells() {
    final var wizardDao = mock(WizardDao.class);
    final var spellbookDao = mock(SpellbookDao.class);
    final var spellDao = mock(SpellDao.class);

    final var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyNoInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllSpells();
    verify(spellDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  void testFindWizardsWithSpellbook() {
    final var bookname = "bookname";
    final var spellbook = mock(Spellbook.class);
    final var wizards = Set.of(mock(Wizard.class), mock(Wizard.class), mock(Wizard.class));
    when(spellbook.getWizards()).thenReturn(wizards);

    final var spellbookDao = mock(SpellbookDao.class);
    when(spellbookDao.findByName(bookname)).thenReturn(spellbook);

    final var wizardDao = mock(WizardDao.class);
    final var spellDao = mock(SpellDao.class);

    final var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyNoInteractions(wizardDao, spellbookDao, spellDao, spellbook);

    final var result = service.findWizardsWithSpellbook(bookname);
    verify(spellbookDao).findByName(bookname);
    verify(spellbook).getWizards();

    assertNotNull(result);
    assertEquals(3, result.size());

    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  void testFindWizardsWithSpell() {
    final var wizards = Set.of(mock(Wizard.class), mock(Wizard.class), mock(Wizard.class));
    final var spellbook = mock(Spellbook.class);
    when(spellbook.getWizards()).thenReturn(wizards);

    final var spellbookDao = mock(SpellbookDao.class);
    final var wizardDao = mock(WizardDao.class);

    final var spell = mock(Spell.class);
    when(spell.getSpellbook()).thenReturn(spellbook);

    final var spellName = "spellname";
    final var spellDao = mock(SpellDao.class);
    when(spellDao.findByName(spellName)).thenReturn(spell);

    final var service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyNoInteractions(wizardDao, spellbookDao, spellDao, spellbook);

    final var result = service.findWizardsWithSpell(spellName);
    verify(spellDao).findByName(spellName);
    verify(spellbook).getWizards();

    assertNotNull(result);
    assertEquals(3, result.size());

    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }
}
