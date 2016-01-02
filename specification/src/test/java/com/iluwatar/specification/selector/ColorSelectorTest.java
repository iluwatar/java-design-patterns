package com.iluwatar.specification.selector;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Color;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Date: 12/29/15 - 7:35 PM
 *
 * @author Jeroen Meulemeester
 */
public class ColorSelectorTest {

  /**
   * Verify if the color selector gives the correct results
   */
  @Test
  public void testColor() {
    final Creature greenCreature = mock(Creature.class);
    when(greenCreature.getColor()).thenReturn(Color.GREEN);

    final Creature redCreature = mock(Creature.class);
    when(redCreature.getColor()).thenReturn(Color.RED);

    final ColorSelector greenSelector = new ColorSelector(Color.GREEN);
    assertTrue(greenSelector.test(greenCreature));
    assertFalse(greenSelector.test(redCreature));

  }

}