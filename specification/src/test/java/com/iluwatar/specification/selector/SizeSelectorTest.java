package com.iluwatar.specification.selector;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Size;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Date: 12/29/15 - 7:43 PM
 *
 * @author Jeroen Meulemeester
 */
public class SizeSelectorTest {

  /**
   * Verify if the size selector gives the correct results
   */
  @Test
  public void testMovement() {
    final Creature normalCreature = mock(Creature.class);
    when(normalCreature.getSize()).thenReturn(Size.NORMAL);

    final Creature smallCreature = mock(Creature.class);
    when(smallCreature.getSize()).thenReturn(Size.SMALL);

    final SizeSelector normalSelector = new SizeSelector(Size.NORMAL);
    assertTrue(normalSelector.test(normalCreature));
    assertFalse(normalSelector.test(smallCreature));
  }

}
