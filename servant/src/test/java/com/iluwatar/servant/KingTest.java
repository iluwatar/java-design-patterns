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

package com.iluwatar.servant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Date: 12/28/15 - 9:40 PM
 *
 * @author Jeroen Meulemeester
 */
public class KingTest {

  @Test
  public void testHungrySoberUncomplimentedKing() {
    final King king = new King();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedSoberUncomplimentedKing() {
    final King king = new King();
    king.getFed();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungryDrunkUncomplimentedKing() {
    final King king = new King();
    king.getDrink();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungrySoberComplimentedKing() {
    final King king = new King();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedDrunkUncomplimentedKing() {
    final King king = new King();
    king.getFed();
    king.getDrink();
    king.changeMood();
    assertTrue(king.getMood());
  }

  @Test
  public void testFedSoberComplimentedKing() {
    final King king = new King();
    king.getFed();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testFedDrunkComplimentedKing() {
    final King king = new King();
    king.getFed();
    king.getDrink();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

  @Test
  public void testHungryDrunkComplimentedKing() {
    final King king = new King();
    king.getDrink();
    king.receiveCompliments();
    king.changeMood();
    assertFalse(king.getMood());
  }

}