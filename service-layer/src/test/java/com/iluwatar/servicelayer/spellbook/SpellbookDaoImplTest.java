package com.iluwatar.servicelayer.spellbook;

import com.iluwatar.servicelayer.common.BaseDaoTest;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/28/15 - 11:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class SpellbookDaoImplTest extends BaseDaoTest<Spellbook, SpellbookDaoImpl> {

  public SpellbookDaoImplTest() {
    super(Spellbook::new, new SpellbookDaoImpl());
  }

  @Test
  public void testFindByName() throws Exception {
    final SpellbookDaoImpl dao = getDao();
    final List<Spellbook> allBooks = dao.findAll();
    for (final Spellbook book : allBooks) {
      final Spellbook spellByName = dao.findByName(book.getName());
      assertNotNull(spellByName);
      assertEquals(book.getId(), spellByName.getId());
      assertEquals(book.getName(), spellByName.getName());
    }
  }

}
