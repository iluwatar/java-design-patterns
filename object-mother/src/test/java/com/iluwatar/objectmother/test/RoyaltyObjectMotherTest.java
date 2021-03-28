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

package com.iluwatar.objectmother.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.objectmother.King;
import com.iluwatar.objectmother.Queen;
import com.iluwatar.objectmother.RoyaltyObjectMother;
import org.junit.jupiter.api.Test;

/**
 * Test Generation of Royalty Types using the object-mother
 */
public class RoyaltyObjectMotherTest {

  @Test
  void unsuccessfulKingFlirt() {
    var soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing();
    var flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    soberUnhappyKing.flirt(flirtyQueen);
    assertFalse(soberUnhappyKing.isHappy());
  }

  @Test
  void queenIsBlockingFlirtCauseDrunkKing() {
    var drunkUnhappyKing = RoyaltyObjectMother.createDrunkKing();
    var notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    drunkUnhappyKing.flirt(notFlirtyQueen);
    assertFalse(drunkUnhappyKing.isHappy());
  }

  @Test
  void queenIsBlockingFlirt() {
    var soberHappyKing = RoyaltyObjectMother.createHappyKing();
    var notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    soberHappyKing.flirt(notFlirtyQueen);
    assertFalse(soberHappyKing.isHappy());
  }

  @Test
  void successfullKingFlirt() {
    var soberHappyKing = RoyaltyObjectMother.createHappyKing();
    var flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    soberHappyKing.flirt(flirtyQueen);
    assertTrue(soberHappyKing.isHappy());
  }

  @Test
  void testQueenType() {
    var flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    var notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    assertEquals(flirtyQueen.getClass(), Queen.class);
    assertEquals(notFlirtyQueen.getClass(), Queen.class);
  }

  @Test
  void testKingType() {
    var drunkKing = RoyaltyObjectMother.createDrunkKing();
    var happyDrunkKing = RoyaltyObjectMother.createHappyDrunkKing();
    var happyKing = RoyaltyObjectMother.createHappyKing();
    var soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing();
    assertEquals(drunkKing.getClass(), King.class);
    assertEquals(happyDrunkKing.getClass(), King.class);
    assertEquals(happyKing.getClass(), King.class);
    assertEquals(soberUnhappyKing.getClass(), King.class);
  }
}
