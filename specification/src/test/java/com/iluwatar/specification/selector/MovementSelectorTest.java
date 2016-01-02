package com.iluwatar.specification.selector;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Date: 12/29/15 - 7:37 PM
 *
 * @author Jeroen Meulemeester
 */
public class MovementSelectorTest {

  /**
   * Verify if the movement selector gives the correct results
   */
  @Test
  public void testMovement() {
    final Creature swimmingCreature = mock(Creature.class);
    when(swimmingCreature.getMovement()).thenReturn(Movement.SWIMMING);

    final Creature flyingCreature = mock(Creature.class);
    when(flyingCreature.getMovement()).thenReturn(Movement.FLYING);

    final MovementSelector swimmingSelector = new MovementSelector(Movement.SWIMMING);
    assertTrue(swimmingSelector.test(swimmingCreature));
    assertFalse(swimmingSelector.test(flyingCreature));

  }

}