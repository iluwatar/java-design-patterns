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
package com.iluwatar.flyweight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/12/15 - 10:54 PM
 *
 * @author Jeroen Meulemeester
 */
public class AlchemistShopTest {

  @Test
  public void testShop() throws Exception {
    final AlchemistShop shop = new AlchemistShop();

    final List<Potion> bottomShelf = shop.getBottomShelf();
    assertNotNull(bottomShelf);
    assertEquals(5, bottomShelf.size());

    final List<Potion> topShelf = shop.getTopShelf();
    assertNotNull(topShelf);
    assertEquals(8, topShelf.size());

    final List<Potion> allPotions = new ArrayList<>();
    allPotions.addAll(topShelf);
    allPotions.addAll(bottomShelf);

    // There are 13 potion instances, but only 5 unique instance types
    assertEquals(13, allPotions.size());
    assertEquals(5, allPotions.stream().map(System::identityHashCode).distinct().count());

  }

}
