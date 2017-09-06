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
package com.iluwatar.servicelayer.magic;

import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spell.SpellDao;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.spellbook.SpellbookDao;
import com.iluwatar.servicelayer.wizard.Wizard;
import com.iluwatar.servicelayer.wizard.WizardDao;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 12/29/15 - 12:06 AM
 *
 * @author Jeroen Meulemeester
 */
public class MagicServiceImplTest {

  @Test
  public void testFindAllWizards() throws Exception {
    final WizardDao wizardDao = mock(WizardDao.class);
    final SpellbookDao spellbookDao = mock(SpellbookDao.class);
    final SpellDao spellDao = mock(SpellDao.class);

    final MagicServiceImpl service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyZeroInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllWizards();
    verify(wizardDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  public void testFindAllSpellbooks() throws Exception {
    final WizardDao wizardDao = mock(WizardDao.class);
    final SpellbookDao spellbookDao = mock(SpellbookDao.class);
    final SpellDao spellDao = mock(SpellDao.class);

    final MagicServiceImpl service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyZeroInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllSpellbooks();
    verify(spellbookDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  public void testFindAllSpells() throws Exception {
    final WizardDao wizardDao = mock(WizardDao.class);
    final SpellbookDao spellbookDao = mock(SpellbookDao.class);
    final SpellDao spellDao = mock(SpellDao.class);

    final MagicServiceImpl service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyZeroInteractions(wizardDao, spellbookDao, spellDao);

    service.findAllSpells();
    verify(spellDao).findAll();
    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  public void testFindWizardsWithSpellbook() throws Exception {
    final String bookname = "bookname";
    final Spellbook spellbook = mock(Spellbook.class);
    final Set<Wizard> wizards = new HashSet<>();
    wizards.add(mock(Wizard.class));
    wizards.add(mock(Wizard.class));
    wizards.add(mock(Wizard.class));

    when(spellbook.getWizards()).thenReturn(wizards);

    final SpellbookDao spellbookDao = mock(SpellbookDao.class);
    when(spellbookDao.findByName(eq(bookname))).thenReturn(spellbook);

    final WizardDao wizardDao = mock(WizardDao.class);
    final SpellDao spellDao = mock(SpellDao.class);


    final MagicServiceImpl service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyZeroInteractions(wizardDao, spellbookDao, spellDao, spellbook);

    final List<Wizard> result = service.findWizardsWithSpellbook(bookname);
    verify(spellbookDao).findByName(eq(bookname));
    verify(spellbook).getWizards();

    assertNotNull(result);
    assertEquals(3, result.size());

    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

  @Test
  public void testFindWizardsWithSpell() throws Exception {
    final Set<Wizard> wizards = new HashSet<>();
    wizards.add(mock(Wizard.class));
    wizards.add(mock(Wizard.class));
    wizards.add(mock(Wizard.class));

    final Spellbook spellbook = mock(Spellbook.class);
    when(spellbook.getWizards()).thenReturn(wizards);

    final SpellbookDao spellbookDao = mock(SpellbookDao.class);
    final WizardDao wizardDao = mock(WizardDao.class);

    final Spell spell = mock(Spell.class);
    when(spell.getSpellbook()).thenReturn(spellbook);

    final String spellName = "spellname";
    final SpellDao spellDao = mock(SpellDao.class);
    when(spellDao.findByName(eq(spellName))).thenReturn(spell);

    final MagicServiceImpl service = new MagicServiceImpl(wizardDao, spellbookDao, spellDao);
    verifyZeroInteractions(wizardDao, spellbookDao, spellDao, spellbook);

    final List<Wizard> result = service.findWizardsWithSpell(spellName);
    verify(spellDao).findByName(eq(spellName));
    verify(spellbook).getWizards();

    assertNotNull(result);
    assertEquals(3, result.size());

    verifyNoMoreInteractions(wizardDao, spellbookDao, spellDao);
  }

}
