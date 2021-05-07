/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
import com.iluwatar.specification.property.Size;
import org.junit.jupiter.api.Test;

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
  void testMovement() {
    final var normalCreature = mock(Creature.class);
    when(normalCreature.getSize()).thenReturn(Size.NORMAL);

    final var smallCreature = mock(Creature.class);
    when(smallCreature.getSize()).thenReturn(Size.SMALL);

    final var normalSelector = new SizeSelector(Size.NORMAL);
    assertTrue(normalSelector.test(normalCreature));
    assertFalse(normalSelector.test(smallCreature));
  }

}
