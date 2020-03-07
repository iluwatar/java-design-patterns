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
import com.iluwatar.specification.property.Color;
import org.junit.jupiter.api.Test;

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
    final var greenCreature = mock(Creature.class);
    when(greenCreature.getColor()).thenReturn(Color.GREEN);

    final var redCreature = mock(Creature.class);
    when(redCreature.getColor()).thenReturn(Color.RED);

    final var greenSelector = new ColorSelector(Color.GREEN);
    assertTrue(greenSelector.test(greenCreature));
    assertFalse(greenSelector.test(redCreature));

  }

}