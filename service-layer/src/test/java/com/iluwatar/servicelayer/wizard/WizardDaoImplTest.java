package com.iluwatar.servicelayer.wizard;

import com.iluwatar.servicelayer.common.BaseDaoTest;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/28/15 - 11:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class WizardDaoImplTest extends BaseDaoTest<Wizard, WizardDaoImpl> {

  public WizardDaoImplTest() {
    super(Wizard::new, new WizardDaoImpl());
  }

  @Test
  public void testFindByName() throws Exception {
    final WizardDaoImpl dao = getDao();
    final List<Wizard> allWizards = dao.findAll();
    for (final Wizard spell : allWizards) {
      final Wizard byName = dao.findByName(spell.getName());
      assertNotNull(byName);
      assertEquals(spell.getId(), byName.getId());
      assertEquals(spell.getName(), byName.getName());
    }
  }

}
