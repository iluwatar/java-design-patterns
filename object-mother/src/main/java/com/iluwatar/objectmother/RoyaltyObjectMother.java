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

package com.iluwatar.objectmother;

/**
 * Object Mother Pattern generating Royalty Types.
 */
public final class RoyaltyObjectMother {

  /**
   * Method to create a sober and unhappy king. The standard parameters are set.
   *
   * @return An instance of {@link com.iluwatar.objectmother.King} with the standard properties.
   */
  public static King createSoberUnhappyKing() {
    return new King();
  }

  /**
   * Method of the object mother to create a drunk king.
   *
   * @return A drunk {@link com.iluwatar.objectmother.King}.
   */
  public static King createDrunkKing() {
    var king = new King();
    king.makeDrunk();
    return king;
  }

  /**
   * Method to create a happy king.
   *
   * @return A happy {@link com.iluwatar.objectmother.King}.
   */
  public static King createHappyKing() {
    var king = new King();
    king.makeHappy();
    return king;
  }

  /**
   * Method to create a happy and drunk king.
   *
   * @return A drunk and happy {@link com.iluwatar.objectmother.King}.
   */
  public static King createHappyDrunkKing() {
    var king = new King();
    king.makeHappy();
    king.makeDrunk();
    return king;
  }

  /**
   * Method to create a flirty queen.
   *
   * @return A flirty {@link com.iluwatar.objectmother.Queen}.
   */
  public static Queen createFlirtyQueen() {
    var queen = new Queen();
    queen.setFlirtiness(true);
    return queen;
  }

  /**
   * Method to create a not flirty queen.
   *
   * @return A not flirty {@link com.iluwatar.objectmother.Queen}.
   */
  public static Queen createNotFlirtyQueen() {
    return new Queen();
  }
}
