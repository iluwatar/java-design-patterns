/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.specification.selector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.property.Mass;
import com.iluwatar.specification.property.Movement;
import org.junit.jupiter.api.Test;

public class CompositeSelectorsTest {

  /**
   * Verify if the conjunction selector gives the correct results.
   */
  @Test
  public void testAndComposition() {
    final Creature swimmingHeavyCreature = mock(Creature.class);
    when(swimmingHeavyCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingHeavyCreature.getMass()).thenReturn(new Mass(100.0));

    final Creature swimmingLightCreature = mock(Creature.class);
    when(swimmingLightCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingLightCreature.getMass()).thenReturn(new Mass(25.0));

    final AbstractSelector<Creature> lightAndSwimmingSelector = new MassSmallerThanOrEqSelector(
        50.0).and(new MovementSelector(Movement.SWIMMING));
    assertFalse(lightAndSwimmingSelector.test(swimmingHeavyCreature));
    assertTrue(lightAndSwimmingSelector.test(swimmingLightCreature));
  }

  /**
   * Verify if the disjunction selector gives the correct results.
   */
  @Test
  public void testOrComposition() {
    final Creature swimmingHeavyCreature = mock(Creature.class);
    when(swimmingHeavyCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingHeavyCreature.getMass()).thenReturn(new Mass(100.0));

    final Creature swimmingLightCreature = mock(Creature.class);
    when(swimmingLightCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingLightCreature.getMass()).thenReturn(new Mass(25.0));

    final AbstractSelector<Creature> lightOrSwimmingSelector = new MassSmallerThanOrEqSelector(50.0)
        .or(new MovementSelector(Movement.SWIMMING));
    assertTrue(lightOrSwimmingSelector.test(swimmingHeavyCreature));
    assertTrue(lightOrSwimmingSelector.test(swimmingLightCreature));
  }

  /**
   * Verify if the negation selector gives the correct results.
   */
  @Test
  public void testNotComposition() {
    final Creature swimmingHeavyCreature = mock(Creature.class);
    when(swimmingHeavyCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingHeavyCreature.getMass()).thenReturn(new Mass(100.0));

    final Creature swimmingLightCreature = mock(Creature.class);
    when(swimmingLightCreature.getMovement()).thenReturn(Movement.SWIMMING);
    when(swimmingLightCreature.getMass()).thenReturn(new Mass(25.0));

    final AbstractSelector<Creature> heavySelector = new MassSmallerThanOrEqSelector(50.0).not();
    assertTrue(heavySelector.test(swimmingHeavyCreature));
    assertFalse(heavySelector.test(swimmingLightCreature));
  }
}
