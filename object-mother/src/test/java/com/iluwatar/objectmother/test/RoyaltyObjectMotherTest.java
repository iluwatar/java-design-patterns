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

package com.iluwatar.objectmother.test;

import com.iluwatar.objectmother.King;
import com.iluwatar.objectmother.Queen;
import com.iluwatar.objectmother.Royalty;
import com.iluwatar.objectmother.RoyaltyObjectMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Generation of Royalty Types using the object-mother
 */
public class RoyaltyObjectMotherTest {

  @Test
  public void unsuccessfulKingFlirt() {
    King soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing();
    Queen flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    soberUnhappyKing.flirt(flirtyQueen);
    assertFalse(soberUnhappyKing.isHappy());
  }

  @Test
  public void queenIsBlockingFlirtCauseDrunkKing() {
    King drunkUnhappyKing = RoyaltyObjectMother.createDrunkKing();
    Queen notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    drunkUnhappyKing.flirt(notFlirtyQueen);
    assertFalse(drunkUnhappyKing.isHappy());
  }

  @Test
  public void queenIsBlockingFlirt() {
    King soberHappyKing = RoyaltyObjectMother.createHappyKing();
    Queen notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    soberHappyKing.flirt(notFlirtyQueen);
    assertFalse(soberHappyKing.isHappy());
  }

  @Test
  public void successfullKingFlirt() {
    King soberHappyKing = RoyaltyObjectMother.createHappyKing();
    Queen flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    soberHappyKing.flirt(flirtyQueen);
    assertTrue(soberHappyKing.isHappy());
  }

  @Test
  public void testQueenType() {
    Royalty flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    Royalty notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen();
    assertEquals(flirtyQueen.getClass(), Queen.class);
    assertEquals(notFlirtyQueen.getClass(), Queen.class);
  }

  @Test
  public void testKingType() {
    Royalty drunkKing = RoyaltyObjectMother.createDrunkKing();
    Royalty happyDrunkKing = RoyaltyObjectMother.createHappyDrunkKing();
    Royalty happyKing = RoyaltyObjectMother.createHappyKing();
    Royalty soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing();
    assertEquals(drunkKing.getClass(), King.class);
    assertEquals(happyDrunkKing.getClass(), King.class);
    assertEquals(happyKing.getClass(), King.class);
    assertEquals(soberUnhappyKing.getClass(), King.class);
  }
}
