package com.iluwatar.servicelayer.spell;

import com.iluwatar.servicelayer.common.BaseDaoTest;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/28/15 - 11:02 PM
 *
 * @author Jeroen Meulemeester
 */
public class SpellDaoImplTest extends BaseDaoTest<Spell, SpellDaoImpl> {

  public SpellDaoImplTest() {
    super(Spell::new, new SpellDaoImpl());
  }

  @Test
  public void testFindByName() throws Exception {
    final SpellDaoImpl dao = getDao();
    final List<Spell> allSpells = dao.findAll();
    for (final Spell spell : allSpells) {
      final Spell spellByName = dao.findByName(spell.getName());
      assertNotNull(spellByName);
      assertEquals(spell.getId(), spellByName.getId());
      assertEquals(spell.getName(), spellByName.getName());
    }
  }

}
